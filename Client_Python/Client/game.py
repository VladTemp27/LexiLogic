import concurrent.futures
import os
import threading
import time
import asyncio
import json
import login
from update_dispatcher import UpdateDispatcher
from public import index as ORBConnection
from Core import json_parser
from concurrent.futures import ThreadPoolExecutor

RESPONSE = {
    'response': None
}


class Game(UpdateDispatcher):
    response: str
    game_room_id = 0

    def __init__(self):
        self.response = ""

    def game_timer(self):
        game_time = self.parse_game_time()
        for i in range(game_time, 0, -1):
            time.sleep(1)
        print("Time's up!")

    def update(self, json_string: str):
        global input_thread
        print('update', json_string)
        self.response = json_string
        state = json_parser.parse_status_state(json_string)

        if state == "staging":
            print("Round ", self.get_current_round(json_string))
            letter_box = self.create_letter_box(json_string)

            if letter_box:
                for row in letter_box:
                    print(row)

            print("5 second Countdown")
            time.sleep(5)
            login.orb.game_service_stub.playerReady(os.environ['username'], self.game_room_id)
            print("Player Sent ready")

        if state == "game_started":
            timer_thread = threading.Thread(target=self.game_timer)
            timer_thread.start()
            input_thread = threading.Thread(target=self.input_prompt)
            input_thread.start()
            timer_thread.join()

        if state == "game_room":
            input_thread = threading.Thread(target=self.input_prompt)
            input_thread.start()

        if state == "game_done":
            self.check_winner(json_string)

        if state == "invalid_word":
            print("INVALID WORD, please try again")
            input_thread = threading.Thread(target=self.input_prompt)
            input_thread.start()

        if state == "self_duplicate":
            print("You've already entered that word!")
            input_thread = threading.Thread(target=self.input_prompt)
            input_thread.start()

        if state == "duped":
            print("You've ben duped")
            input_thread = threading.Thread(target=self.input_prompt)
            input_thread.start()

        if state == "duped_word":
            print("You have duped someone!")
            input_thread = threading.Thread(target=self.input_prompt)
            input_thread.start()

    def init_components(self):
        global RESPONSE
        try:
            self.game_room_id = json_parser.parse_game_room(RESPONSE['response'])
            print("Handshake in progress")
            login.orb.game_service_stub.readyHandshake(os.environ['username'], self.game_room_id)
            print("Handshake success")

        except Exception as e:
            print(e)

        while True:
            pass

    def get_current_round(self, json_string):
        try:
            data = json.loads(json_string)
            return data.get("current_round", None)
        except json.JSONDecodeError as e:
            print("Error parsing JSON:", e)
            return None
        pass

    def create_letter_box(self, json_string):
        try:
            data = json.loads(json_string)
            char_matrix = data.get("char_matrix", None)
            if not char_matrix:
                print("Character matrix not found in JSON.")
                return None
            return char_matrix
        except json.JSONDecodeError as e:
            print("Error parsing JSON:", e)
            return None
        pass

    def submit_word(self, word, username, room_id):
        login.orb.game_service_stub.verifyWord(word, username, room_id)
        pass

    def input_prompt(self):
        word = input("\nEnter a word (5 Letters Or More)\n")
        print("SUBMITTING A WORD...")
        try:
            self.submit_word(word, login.CURRENT_USER['username'], self.game_room_id)
        except Exception as e:
            print(e)
        pass

    def countdown_timer(self):
        for i in range(5, 0, -1):
            time.sleep(1)
        pass

    def check_winner(self, json_string):
        data = json.loads(json_string)
        winner = data["winner"]
        print("Winner", winner)

        if login.CURRENT_USER['username'] == winner:
            print("VICTORY")
        else:
            print("DEFEAT")
        pass
        exit(0)

    def parse_game_time(self):
        global RESPONSE
        data = json.loads(RESPONSE['response'])
        game_time = data['gameTime']
        return int(game_time)
        pass


class Match:
    def find_match(self):
        global RESPONSE
        RESPONSE['response'] = login.orb.game_service_stub.matchMake(
            login.orb.poa.servant_to_reference(login.CURRENT_USER['player_callback_impl']))

        status = self.parse_match_making(RESPONSE['response'])

        if status == 'timeout':
            print("MATCHMAKE FAILED")
            return False
            pass
        else:
            print('findmatch', RESPONSE['response'])
            return True

    def parse_match_making(self, json_string):
        data = json.loads(json_string)
        status = data['status']
        return status
