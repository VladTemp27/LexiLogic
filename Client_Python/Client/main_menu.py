import os
import sys
import login
from game import Game
from game import Match
import match_history
import leaderboards
import profile

def main_menu_prompt():
    os.system('clr||cls')
    print("****** Main Menu *******")
    print("1. Play")
    print("2. Match History")
    print("3. Leaderboards")
    print("4. Profile")
    print("5. Exit")
    print("************************")
    choice = input("choice: ")

    if choice == "1":
        game = Game()
        login.CURRENT_USER['player_callback_impl'].controller_interface(game)
        match = Match()

        if match.find_match():
            game.init_components()

    elif choice == "2":
        match_history.display_match_history()
    elif choice == "3":
        leaderboards.display_leaderboard()
    elif choice == "4":
        profile.display_profile()
    elif choice == "5":
        login.orb.player_service_stub.logout(login.CURRENT_USER['username'])
        sys.exit(1)
