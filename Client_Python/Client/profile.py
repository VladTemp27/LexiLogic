import os
import login
import time
from public import index as ORBConnection


def display_profile():
    while True:
        os.system('cls||clr')
        username = os.environ.get("username")
        print(f"Username: {username}")
        print("**********************")
        print("1. Change Username")
        print("2. Change Password")
        print("3. Back")
        print("**********************")
        choice = input("Choice: ")

        if choice == "1":
            change_username()
            break
        elif choice == "2":
            change_password()
            break
        elif choice == "3":
            # go back
            pass


def change_password():
    while True:
        os.system('cls||clr')
        print("**** Change Password *******")
        prev_pass = input("Old Password: ")
        new_pass = input("New Password: ")
        confirm_pass = input("Confirm Password: ")

        if new_pass == confirm_pass:
            if prev_pass == login.CURRENT_USER['password']:
                try:
                    orb = ORBConnection.orb_connection()
                    nce = ORBConnection.get_nce(orb)
                    player_service_stub = ORBConnection.get_player_service_stub(nce)
                    player_service_stub.changePassword(login.CURRENT_USER['password'], new_pass)
                except Exception as e:
                    print(e)
                break
            else:
                print("Old password do not match please try again")
        else:
            print("New password is not the same as confirm password")
            time.sleep(0.4)

    print("Your password has been changed")


def change_username():
    while True:
        os.system('cls||clr')
        # currentUsername = os.environ.get('username')
        print("******* Change Username ********")
        new_username = input("Enter New Username: ")
        if not login.CURRENT_USER['username'] == new_username and check_username_existence(new_username):
            try:
                orb = ORBConnection.orb_connection()
                nce = ORBConnection.get_nce(orb)
                player_service_stub = ORBConnection.get_player_service_stub(nce)
                player_service_stub.changeUsername(login.CURRENT_USER['username'], new_username)
            except Exception as e:
                print(e)
            break
        else:
            print("Username is invalid, username already exists or is the same as the current username")

    print("Your username has been changed")


def check_username_existence(uname: str):
    #Use DAL to check if username is already taken
    return True
