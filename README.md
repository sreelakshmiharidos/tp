[![CI Status](https://github.com/se-edu/addressbook-level3/workflows/Java%20CI/badge.svg)](https://github.com/AY2425S2-CS2103T-T15-2/tp/actions)

![Ui](docs/images/Ui.png)

#### This project is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org).
# JCRoster+
#### JCRoster+ streamlines contact management for Junior College homeroom teachers.

* by providing quick access to student, and guardian details.
* By integrating a smart tagging system it allows for quick categorization and seamless student grouping based on academic strengths.
* Ultimately, it reduces administrative hassle and improves efficiency in managing classroom interactions.

## Features:
### Add Students ✏️
#### Adds a student to the address book.
`add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]...`

Example commands:<br>
>add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01<br><br>
add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal


### Delete Student 🗑️
#### Removes a student’s record from the address book.
`delete UID` <br>
Example commands:<br>
>delete 1234<br>
delete 98765


### Find Person 🔍
#### Searches for a person in the address book based on their name.

`find KEYWORD [MORE_KEYWORDS]`

Example commands:<br>
>find John<br>
find Betsy Crowe

### Exit Program 🚪
#### Terminates the application

`exit`<br>
Example command:
>exit

**More features coming up...**
* For the detailed documentation of this project, see the **[JCRosters](https://ay2425s2-cs2103t-t15-2.github.io/tp/)**.
