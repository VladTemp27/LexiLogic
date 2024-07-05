from public import index as ORBConnection
from Core import json_parser
import login


def get_match_history_data():
    orb = ORBConnection.orb_connection()
    nce = ORBConnection.get_nce(orb)
    player_service_stub = ORBConnection.get_player_service_stub(nce)
    match_history_list = json_parser.parse_match_history_json_string(player_service_stub.getGameHistory(login.CURRENT_USER['username']))

    return match_history_list


def display_match_history_prompt(match_history_list):
    print("****** Match History *******")
    print("Standing | Score")
    print("------------------------")
    for match_history in match_history_list:
        if match_history.get_winner() == login.CURRENT_USER['username']:
            print(f"Win | {match_history.get_winner_score()}")
        else:
            print(f"Lose | {match_history.get_winner_score()}")
    print("************************")


def display_match_history():
    match_history_data = get_match_history_data()
    display_match_history_prompt(match_history_data)
