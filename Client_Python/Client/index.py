import os
import sys
import time
import login
import main_menu


def index_view():
    print("*****************")
    print("1. Login")
    print("2. Exit")
    print("*****************")


def index():
    while True:
        os.system('cls||clr')
        index_view()
        user_choice = input()
        if user_choice == "1":
            valid = login.login_view()
            if valid:
                main_menu.main_menu_prompt()
            break
        elif user_choice == "2":
            sys.exit(1)
        else:
            print("Invalid Choice")
            time.sleep(0.3)


if __name__ == "__main__":
    index()
