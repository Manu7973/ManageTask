# TaskManager App

📂 Features Implemented - 
✅ Add new task with Title, Description, Due Date, Priority, and Status
✅ Edit existing task via dialog
✅ Delete task
✅ Mark task as Completed or Pending
✅ Search tasks by title
✅ Filter tasks (All, Completed, Pending)
✅ Toggle between List & Grid view
✅ Priority-based color scheme

High → 🔴 Red
Medium → 🟠 Orange
Low → 🟢 Green


📷 Screens - 
Task List Screen → Displays all tasks with search, filter & toggle view options.
Add/Edit Task Dialog → Form to add or update tasks.
Empty State → Shown when no tasks are added.


📖 Architecture Justification -
Used MVVM to clearly separate concerns:
Model: Room entities & repository for data operations
View: XML layouts + RecyclerView adapters
ViewModel: Exposes LiveData/Flow for observing changes
This improves testability, maintainability, and scalability of the codebase.

<table>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/cf96c626-af67-4a1a-bd29-e0457ae29760" width="300" /></td>
    <td><img src="https://github.com/user-attachments/assets/1ad377ba-c9d3-4ca8-8448-8ab16ba660ef" width="300" /></td>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/20bb3d49-a7b4-4d37-991e-20f4e6f3cbde" width="300" /></td>
    <td><img src="https://github.com/user-attachments/assets/7a1d1c65-f336-4009-b886-630250719704" width="300" /></td>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/4e7c17d0-e0ab-4d4e-a5d5-c62e7cc01cdc" width="300" /></td>
    <td><img src="https://github.com/user-attachments/assets/4738e81e-26a4-42d8-a14b-598a2cd89392" width="300" /></td>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/e9a79af3-e7ed-479c-8ed9-31208e08d56a" width="300" /></td>
    <td><img src="https://github.com/user-attachments/assets/fbed3360-e663-4e54-9c1d-7f9ebfbb49fe" width="300" /></td>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/c8db7558-9dfc-44b8-8332-2ea9551cb1a2" width="300" /></td>
    <td></td>
  </tr>
</table>



👨‍💻 Author - 
# Submitted by - Manthan Aggarwal
