import json
from Utilities.Reference_Objects import LeaderBoard
from Utilities.Reference_Objects import Lobby


def parse_leaderboard_json_string(json_string):
    data = json.loads(json_string)
    leaderboard_json_string = data['leaderboard']
    leaderboard_list = []
    for idx, leaderboard in enumerate(leaderboard_json_string, start=1):
        leaderboard_item = LeaderBoard.LeaderBoard(
            leader_board_id=idx,
            username=leaderboard['username'],
            points=leaderboard['pts'],
            rank=leaderboard['rank']
        )
        leaderboard_list.append(leaderboard_item)

    return leaderboard_list


def parse_match_history_json_string(json_string):
    data = json.loads(json_string)
    lobby_json_string = data['lobby']
    lobby_list = []
    for idx, lobby in enumerate(lobby_json_string, start=1):
        lobby_item = Lobby.Lobby(
            lobby_id=lobby['lobbyID'],
            winner=lobby['winner'],
            winner_score=lobby['score']
        )

        lobby_list.append(lobby_item)

    return lobby_list


def parse_match_making(match_status):
    data = json.loads(match_status)
    status = data['status']
    return status


def parse_game_room(game_room):
    data = json.loads(game_room)
    game_room_id = data['gameRoomID']
    return game_room_id


def parse_room(room):
    data = json.loads(room)
    room_id = data['room_id']
    return int(room_id)


def parse_status_state(status):
    data = json.loads(status)
    state = data["state"]
    return state