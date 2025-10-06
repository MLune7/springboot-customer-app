async function getCustomers() {
  const response = await fetch("/api/customer");
  return await response.json();
}

function renderCustomers(customers) {
  const tableBody = document.getElementById("TP");
  tableBody.innerHTML = "";

  customers.forEach(c => {
    const row = document.createElement("tr");
    row.innerHTML = `
      <td>${c.id}</td>
      <td>${c.firstName}</td>
      <td>${c.lastName}</td>
      <td>${c.balance}</td>
      <td>${c.category}</td>
      <td>${c.points}</td>
      <td>
        <button onclick='openEditModal(${JSON.stringify(c)})'>Edit</button>
        <button onclick="deleteCustomer(${c.id})">Delete</button>
      </td>
    `;
    tableBody.appendChild(row);
  });
}

async function retrieveCustomers() {
  const customers = await getCustomers();
  renderCustomers(customers);
  drawChart(customers);
}

async function addCustomer() {
  const firstName = document.getElementById('firstName').value;
  const lastName = document.getElementById('lastName').value;
  const balance = parseInt(document.getElementById('balance').value);

  await fetch("/api/customer", {
    method: 'POST',
    headers: {'Content-Type': 'application/json'},
    body: JSON.stringify({ firstName, lastName, balance })
  });
  retrieveCustomers();
}

async function editBalance(id) {
  const balance = parseInt(document.getElementById('balance').value);
  await fetch("/api/customer/edit", {
    method: 'POST',
    headers: {'Content-Type': 'application/json'},
    body: JSON.stringify({id,balance})
  });
  retrieveCustomers();
}

async function deleteCustomer(id) {
  await fetch(`/api/customer/${id}`, { method: "DELETE" });
  retrieveCustomers();
}

let editingId = null;
function openEditModal(c) {
  editingId = c.id;
  document.getElementById("editFirstName").value = c.firstName;
  document.getElementById("editLastName").value = c.lastName;
  document.getElementById("editBalance").value = c.balance;
  document.getElementById("editModal").style.display = "block";
}

async function saveCustomer() {
  const firstName = document.getElementById("editFirstName").value;
  const lastName = document.getElementById("editLastName").value;
  const balance = parseInt(document.getElementById("editBalance").value);

  await fetch("/api/customer", {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ id: editingId, firstName, lastName, balance })
  });

  document.getElementById("editModal").style.display = "none";
  retrieveCustomers();
}

async function filterCategory() {
  const category = document.getElementById("filterCategory").value;
  let customers;

  if (category === "All") {
    customers = await getCustomers();
  } else {
    const response = await fetch(`/api/customer/category/${category}`);
    customers = await response.json();
  }
  renderCustomers(customers);
  drawChart(customers);
}

async function loadPage(page) {
  const response = await fetch(`/api/customer/paged?page=${page}&size=5`);
  const data = await response.json();
  renderCustomers(data.content);
  drawChart(data.content);
}

async function downloadCSV() {
  window.location.href = "/api/customer/export";
}

// Chart
function drawChart(customers) {
  const counts = { VIP: 0, Premium: 0, Regular: 0 };

  customers.forEach(c => counts[c.category]++);

  const ctx = document.getElementById('categoryChart').getContext('2d');
  new Chart(ctx, {
    type: 'pie',
    data: {
      labels: ['VIP', 'Premium', 'Regular'],
      datasets: [{
        data: [counts.VIP, counts.Premium, counts.Regular]
      }]
    }
  });
}
