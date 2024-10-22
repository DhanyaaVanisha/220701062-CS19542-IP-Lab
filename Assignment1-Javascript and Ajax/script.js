document.addEventListener('DOMContentLoaded', function() {
    const taskList = document.getElementById('task-list');
  
    // Function to render tasks
    function renderTasks(tasks) {
      taskList.innerHTML = ''; // Clear existing tasks
      tasks.forEach(task => {
        const taskItem = document.createElement('div');
        taskItem.className = `task-item ${task.completed ? 'completed' : ''}`;
        taskItem.innerHTML = `
          <input type="checkbox" ${task.completed ? 'checked' : ''} data-id="${task.id}">
          <span>${task.title} - ${task.description}</span>
          <span>${task.dueDate}</span>
          <button class="edit-btn" data-id="${task.id}">Edit</button>
          <button class="delete-btn" data-id="${task.id}">Delete</button>
        `;
        taskList.appendChild(taskItem);
      });
    }
  
    // Fetch tasks from server (AJAX)
    function fetchTasks() {
      // Sample AJAX Request
      const xhr = new XMLHttpRequest();
      xhr.open('GET', '/tasks', true); // URL to your backend API endpoint
      xhr.onload = function() {
        if (xhr.status === 200) {
          const tasks = JSON.parse(xhr.responseText);
          renderTasks(tasks);
        } else {
          console.error('Failed to fetch tasks');
        }
      };
      xhr.send();
    }
  
    // Add task using AJAX
    document.getElementById('add-task-btn').addEventListener('click', function() {
      const title = document.getElementById('task-title').value;
      const description = document.getElementById('task-desc').value;
      const dueDate = document.getElementById('task-date').value;
  
      if (title && description && dueDate) {
        const xhr = new XMLHttpRequest();
        xhr.open('POST', '/add-task', true);
        xhr.setRequestHeader('Content-Type', 'application/json');
        xhr.onload = function() {
          if (xhr.status === 200) {
            fetchTasks(); // Refresh task list
          } else {
            console.error('Failed to add task');
          }
        };
        xhr.send(JSON.stringify({ title, description, dueDate }));
      } else {
        alert('Please fill in all fields');
      }
    });
  
    // Initial fetch of tasks
    fetchTasks();
  });
  