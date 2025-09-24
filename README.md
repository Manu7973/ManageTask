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

<img width="1080" height="2400" alt="1" src="https://github.com/user-attachments/assets/cf96c626-af67-4a1a-bd29-e0457ae29760" />


👨‍💻 Author - 
# Submitted by - Manthan Aggarwal
