from Core import json_parser
from public import index as ORBConnection


def get_leaderboard_data():
    orb = ORBConnection.orb_connection()
    nce = ORBConnection.get_nce(orb)
    game_service_stub = ORBConnection.get_game_service_stub(nce)
    leaderboard_list = json_parser.parse_leaderboard_json_string(game_service_stub.getLeaderboards())
    return leaderboard_list


def display_leaderboard_prompt(leaderboard_list):
    print("****** Leaderboard *******")
    print("Rank | Name      | Score")
    print("------------------------")
    for leaderboard in leaderboard_list:
        print(f"{leaderboard.get_rank():3d} | {leaderboard.get_username():10s} | {leaderboard.get_points()}")
    print("************************")


def display_leaderboard():
    leaderboard_data = get_leaderboard_data()
    display_leaderboard_prompt(leaderboard_data)


if __name__ == "__main__":
    display_leaderboard()
