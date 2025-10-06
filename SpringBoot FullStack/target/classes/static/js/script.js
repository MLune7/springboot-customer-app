
async function getCustomers() {
  const response = await fetch("/api/customer");
  return await response.json();
}

async function retrieveCustomers() {
  const customers = await getCustomers();
  const tableBody = document.getElementById("TP");
  tableBody.innerHTML = "";

  customers.forEach(c => {
    const row = document.createElement("tr");
    row.innerHTML = ``
      <td>${c.id}</td>
      <td>${c.firstName}</td>
      <td>${c.lastName}</td>
      <td>${c.balance}</td>
      <td>${c.category}</td>
      <td><button onclick="editBalance(${c.id})">Edit</button></td>
    `;
    tableBody.appendChild(row);
  });
}

async function addCustomer() {
  const firstName = document.getElementById('firstName').value;
  const lastName = document.getElementById('lastName').value;
  const balance = parseInt(document.getElementById('balance').value);
  const category = balance > 5000 ? "VIP" : balance > 1000 ? "Premium" : "Regular";
  await fetch("/api/customer", {
    method: 'POST',
    headers: {'Content-Type': 'application/json'},
    body: JSON.stringify({ firstName, lastName, balance, category })
  });
  retrieveCustomers();
}

async function editBalance(id) {
 const balance = parseInt(document.getElementById('balance').value);
  await fetch("/api/customer/edit", {
    method: 'post',
    headers: {'Content-Type': 'application/json'},
    body: JSON.stringify({id,balance})
  });
  retrieveCustomers();
}