const API = "http://localhost:8080/api";

function showSection(section) {

    document.getElementById("dashboard").classList.add("hidden");
    document.getElementById("products").classList.add("hidden");
    document.getElementById("billing").classList.add("hidden");
    document.getElementById("reports").classList.add("hidden");

    document.getElementById(section).classList.remove("hidden");

    if (section === "dashboard") {
        loadDashboard();
    }

    if (section === "products") {
        loadAllProducts();
    }
}


// Dashboard
function loadDashboard() {

    fetch(API + "/reports/product-count")
        .then(r => r.json())
        .then(data => {
            document.getElementById("productCount").innerText = data;
        });

    fetch(API + "/reports/bill-count")
        .then(r => r.json())
        .then(data => {
            document.getElementById("billCount").innerText = data;
        });

    const today = new Date().toISOString().split("T")[0];

    fetch(API + "/bills/sales?date=" + today)
        .then(r => r.json())
        .then(data => {
            document.getElementById("sales").innerText =
                "₹" + data.totalSales;
        });

    fetch(API + "/reports/low-stock")
        .then(r => r.json())
        .then(data => {
            document.getElementById("lowStock").innerText =
                data.length;
        });

    loadProducts();
}


// Add Product
function addProduct() {

    const product = {
        name: document.getElementById("name").value,
        price: Number(document.getElementById("price").value),
        stockQuantity: Number(
            document.getElementById("stock").value
        ),
        reorderThreshold: Number(
            document.getElementById("threshold").value
        )
    };

    fetch(API + "/products", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(product)
    })
    .then(r => r.json())
    .then(data => {

        alert("Product added!");

        document.getElementById("name").value = "";
        document.getElementById("price").value = "";
        document.getElementById("stock").value = "";
        document.getElementById("threshold").value = "";

        loadAllProducts();
        loadDashboard();
    });
}


// Products
function loadProducts() {

    fetch(API + "/products")
        .then(r => r.json())
        .then(products => {

            let html = "";

            products.forEach(p => {

                html += `
                    <div class="product">
                        <b>${p.name}</b><br>
                        Price: ₹${p.price}<br>
                        Stock: ${p.stockQuantity}
                    </div>
                `;
            });

            document.getElementById("productList").innerHTML = html;
        });
}


function loadAllProducts() {

    fetch(API + "/products")
        .then(r => r.json())
        .then(products => {

            let html = "";

            products.forEach(p => {

                html += `
                    <div class="product">
                        <b>ID: ${p.id}</b><br>
                        Product: ${p.name}<br>
                        Price: ₹${p.price}<br>
                        Stock: ${p.stockQuantity}
                    </div>
                `;
            });

            document.getElementById("allProducts").innerHTML =
                html;
        });
}


// Create Bill
function createBill() {

    const productId =
        Number(document.getElementById("billProductId").value);

    const quantity =
        Number(document.getElementById("billQuantity").value);

    const bill = {
        items: [
            {
                product: {
                    id: productId
                },
                quantity: quantity
            }
        ]
    };

    fetch(API + "/bills", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(bill)
    })
    .then(r => r.json())
    .then(data => {

        document.getElementById("billResult").innerHTML =
            `
            <h3>Bill Created ✅</h3>
            Bill ID: ${data.id}<br>
            Total: ₹${data.totalAmount}
            `;

        loadDashboard();
    });
}


// Reports
function loadReports() {

    fetch(API + "/reports/low-stock")
        .then(r => r.json())
        .then(data => {

            let html = "<h3>Low Stock Products</h3>";

            if (data.length === 0) {
                html += "<p>No low stock products 🎉</p>";
            }

            data.forEach(p => {
                html += `
                    <p>
                    ${p.name} -
                    Stock: ${p.stockQuantity}
                    </p>
                `;
            });

            document.getElementById("reportData").innerHTML =
                html;
        });
}


// Load dashboard initially
loadDashboard();