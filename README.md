<h1>LeetCode Simulator</h1>

<p>
  <b>Demo:</b> 
  <a href="https://cattle-avatar-anguished.ngrok-free.dev/login">
    https://cattle-avatar-anguished.ngrok-free.dev/login
  </a>
</p>

<h2>Brief Description</h2>
<p>
An website that mimics functionalities of online judge system.<br>
It allows users to write, submit, and evaluate Java solutions against predefined test cases.<br>
This project is built to support a few students struggle with module introductory programming in university learning basic programming and algorithms,<br>
the application focuses on clarity, simplicity, and educational value.
</p>

<h2>Tech Stack</h2>
<ul>
  <li>Backend: Spring Boot(Java)</li>
  <li>Session Management: Http Session</li>
  <li>Build Tool: Maven</li>
  <li>Database: MySQL</li>
</ul>

<h2>System Architecture</h2>
<ul>
  <li>RESTful API for submission handling</li>
  <li>HTTP Session to track user state</li>
  <li>Judge Service to:
    <ul>
      <li>Compile Java code dynamically</li>
      <li>Execute against test cases</li>
      <li>Return verdict (Accepted / Wrong Answer / Runtime Error)</li>
    </ul>
  </li>
  <li>Deployment: Docker, Ngrok</li>
</ul>

<h2>Core Features</h2>

<h3>Login Page</h3>
<ul>
  <li>Security login system for users</li>
  <li>Passwords are hashed before storing (e.g., BCrypt)</li>
  <li>Role-based access control(Admin/Member)</li>
</ul>

<details>
  <summary>📸 View Login Page</summary>
  <p align="center">
    <img src="screenshots/login.png" width="70%">
  </p>
</details>

<hr>

<h3>Dashboard</h3>
<ul>
  <li>View a welcome message for the user</li>
  <li>Simple and friendly interface for users</li>
</ul>

<details>
  <summary>📸 View Dashboard</summary>
  <p align="center">
    <img src="screenshots/dashboard.png" width="70%">
  </p>
</details>

<hr>

<h3>Problem Page</h3>
<ul>
  <li>Display a list of available programming problems</li>
  <li>Show problem titles and descriptions</li>
  <li>Allow users to view detailed problem statements</li>
  <li>Admin can manage and post new problems with convenient add-problem interface</li>
</ul>

<details>
  <summary>📸 Problem Page - Admin View</summary>
  <p align="center">
    <img src="screenshots/problem-page-admin-view.png" width="70%">
  </p>
</details>

<details>
  <summary>📸 Add Problem Page - Admin View</summary>
  <p align="center">
    <img src="screenshots/add-problem-page.png" width="70%">
  </p>
</details>

<details>
  <summary>📸 Problem Page - Member View</summary>
  <p align="center">
    <img src="screenshots/problem-page-member-view.png" width="70%">
  </p>
</details>

<hr>

<h3>Code Editor Page</h3>
<ul>
  <li>Interactive code editor for writing and editing Java code</li>
  <li>Display the problem details alongside the editor box</li>
  <li>Submit code directly for evaluation</li>
</ul>

<details>
  <summary>📸 View Code Editor</summary>
  <p align="center">
    <img src="screenshots/code-editor.png" width="70%">
  </p>
</details>

<hr>

<h3>Result Page</h3>
<ul>
  <li>Display the result of code execution</li>
  <li>Show verdict: Accepted, Wrong Answer, Time Limit Exceeded, Runtime Error, Compile Error</li>
  <li>Provide feedback for each submission</li>
</ul>

<details>
  <summary>📸 Accepted Result</summary>
  <p align="center">
    <img src="screenshots/Accepted.png" width="70%">
  </p>
</details>

<details>
  <summary>📸 Wrong Answer - With wrong test cases</summary>
  <p align="center">
    <img src="screenshots/WA.png" width="70%">
  </p>
</details>

<details>
  <summary>📸 Time Limit Exceeded</summary>
  <p align="center">
    <img src="screenshots/TLE.png" width="70%">
  </p>
</details>

<hr>

<h2>Usage</h2>
<p>
The system has been deployed and actively used by a small group of students studying introductory programming.<br>
It was designed to assist learners who had difficulty passing basic programming courses.<br>
By providing a simple and interactive coding environment, the platform helped users better understand problem-solving and improve their coding skills.
</p>