# Business CRUD Application

## Description
This project is a JavaFX-based CRUD application developed using the Model–View–Controller (MVC) architecture. 
The application allows the management of multiple Business Entities (such as Clients, Orders, Products, etc.) through a structured and user-friendly graphical interface built entirely with FXML and Scene Builder. 
It starts with a Login View where users authenticate using unique credentials, and upon successful login, they are redirected to a Home View to select which business entity to manage.
Each entity has its own dedicated View, Controller, and Model, enabling full Create, Read, Update, and Delete (CRUD) operations.

---

## Application Overview
Startup & Login:
- The application launches with a Login View.
- Each team member has unique login credentials.
- The Login Controller validates credentials and opens the Home View upon success.

Home View:
- Displays a menu of available Business Entities.
- The number of entities corresponds to the number of team members.
- Allows navigation to the selected Entity View.

Entity Management:
 Each Business Entity has:
- Its own FXML View.
- Its own Controller.
- Its own Model and Entity Store.

Entity Views allow the user to:
- Create new entities.
- View existing entities using a TableView.
- Update entity information.
- Delete entities.

---

## Features
- JavaFX desktop application using MVC architecture.
- Fully designed UI using FXML and Scene Builder.
- Secure login system with unique user credentials.
- Home dashboard for entity selection.
- CRUD operations for multiple business entities.
- TableView-based entity visualization.
- Team-based development using Git/GitHub.

---

## Technologies Used
- Java
- JavaFX
- FXML
- Scene Builder
- Maven
- Git & GitHub

---

## Project Structure:
│
├─ src/
│  └─ main/
│     ├─ java/
│     │  ├─ model/          # Business entity classes
│     │  ├─ store/          # Entity store classes (in-memory)
│     │  ├─ controller/     # Controllers for each view
│     │  └─ main/           # Application launcher
│     │
│     └─ resources/
│        └─ fxml/            # All FXML views created with Scene Builder
│
└─ README.md                 # Project documentation

---

## How to Run
1. Clone the repository: `git clone <repo-url>`
2. Open the project in your preferred IDE (e.g., Eclipse, IntelliJ)
3. Ensure JavaFX libraries are correctly set up
4. Run the `Main.java` launcher

---

## Team Collaboration
- A remote GitHub repository named “Business CRUD Application” was created by the team leader.
- All team members were added as collaborators.
- Each team member was responsible for:
 - Implementing one Business Entity.
 - Developing its Model, View (FXML), and Controller.
- GitHub was used for version control, collaboration, and code integration.

---

## Learning Outcomes
- Applying the MVC architectural pattern in a JavaFX application.
- Designing graphical interfaces using FXML and Scene Builder.
- Implementing CRUD functionality without a database.
- Structuring a scalable JavaFX project.
- Collaborating efficiently using Git and GitHub.
- Preparing the application for future database integration.

---

## Future Work
- Introduce role-based access control (e.g., admin, manager, viewer).
- Restrict which users can create, update, or delete entities.
- Enhance security and multi-user collaboration.
- Add the ability to export entity data to CSV, Excel, or PDF.
- Generate summary reports (e.g., total orders, client activity...).
  

