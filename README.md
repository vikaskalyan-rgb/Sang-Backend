# 🥛 Aavin Milk Delivery — Backend API
### Spring Boot 3.2 · PostgreSQL (Neon Tech) · Deploy on Render

---

## 📁 Project Structure

```
aavin-backend/
├── pom.xml
└── src/main/
    ├── java/com/aavin/delivery/
    │   ├── AavinDeliveryApplication.java
    │   ├── config/
    │   │   └── CorsConfig.java
    │   ├── controller/
    │   │   ├── AuthController.java
    │   │   ├── CustomerController.java
    │   │   ├── DailyDeliveryController.java
    │   │   ├── DashboardController.java
    │   │   ├── ExpenseController.java
    │   │   ├── HealthController.java
    │   │   ├── PacketTypeController.java
    │   │   ├── PaymentController.java
    │   │   ├── StockController.java
    │   │   └── SupplierController.java
    │   ├── dto/         (all request/response DTOs)
    │   ├── entity/      (JPA entities)
    │   ├── exception/   (custom exceptions + global handler)
    │   ├── repository/  (Spring Data JPA repos)
    │   └── service/     (business logic)
    └── resources/
        └── application.properties
```

---

## 🗄️ STEP 1 — Set Up Neon Tech PostgreSQL

1. Go to **https://neon.tech** → Sign up (free tier)
2. Create a new project → name it `aavin-delivery`
3. Create a new database → name it `aavindb`
4. Click **"Connection Details"** → copy the **Connection String**
   It looks like:
   ```
   postgresql://username:password@ep-xxx.us-east-2.aws.neon.tech/aavindb?sslmode=require
   ```
5. Run the SQL schema below in the **Neon SQL Editor**

### 📋 SQL Schema (run this in Neon SQL Editor)

```sql
-- Packet Types
CREATE TABLE IF NOT EXISTS packet_types (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    color VARCHAR(50) NOT NULL,
    color_hex VARCHAR(10) NOT NULL,
    price_per_packet DECIMAL(10,2) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

-- Suppliers
CREATE TABLE IF NOT EXISTS suppliers (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    address TEXT,
    notes TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

-- Customers
CREATE TABLE IF NOT EXISTS customers (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    address TEXT,
    latitude DECIMAL(10,8),
    longitude DECIMAL(11,8),
    delivery_order INTEGER NOT NULL,
    default_packets INTEGER DEFAULT 2,
    birthday DATE,
    notes TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    packet_config_type VARCHAR(20) DEFAULT 'DAILY',
    default_packet_type_id BIGINT REFERENCES packet_types(id),
    packet_count INTEGER DEFAULT 2,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

-- Daily Deliveries
CREATE TABLE IF NOT EXISTS daily_deliveries (
    id BIGSERIAL PRIMARY KEY,
    customer_id BIGINT NOT NULL REFERENCES customers(id),
    packet_type_id BIGINT REFERENCES packet_types(id),
    delivery_date DATE NOT NULL,
    is_delivered BOOLEAN DEFAULT FALSE,
    packets_delivered INTEGER,
    substitute_name VARCHAR(255),
    delivered_at TIMESTAMP,
    notes TEXT,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    UNIQUE(customer_id, delivery_date)
);

-- Payments
CREATE TABLE IF NOT EXISTS payments (
    id BIGSERIAL PRIMARY KEY,
    customer_id BIGINT NOT NULL REFERENCES customers(id),
    amount DECIMAL(10,2) NOT NULL,
    payment_date DATE NOT NULL,
    billing_month INTEGER NOT NULL,
    billing_year INTEGER NOT NULL,
    payment_method VARCHAR(50) DEFAULT 'CASH',
    notes TEXT,
    created_at TIMESTAMP DEFAULT NOW()
);

-- Stock
CREATE TABLE IF NOT EXISTS stock (
    id BIGSERIAL PRIMARY KEY,
    packet_type_id BIGINT NOT NULL UNIQUE REFERENCES packet_types(id),
    quantity INTEGER NOT NULL DEFAULT 0,
    updated_at TIMESTAMP DEFAULT NOW()
);

-- Stock Transactions
CREATE TABLE IF NOT EXISTS stock_transactions (
    id BIGSERIAL PRIMARY KEY,
    packet_type_id BIGINT NOT NULL REFERENCES packet_types(id),
    transaction_type VARCHAR(30) NOT NULL,
    quantity INTEGER NOT NULL,
    unit_cost DECIMAL(10,2),
    supplier_id BIGINT REFERENCES suppliers(id),
    reference_id BIGINT,
    notes TEXT,
    transaction_date DATE NOT NULL DEFAULT CURRENT_DATE,
    created_at TIMESTAMP DEFAULT NOW()
);

-- Expenses
CREATE TABLE IF NOT EXISTS expenses (
    id BIGSERIAL PRIMARY KEY,
    category VARCHAR(100) NOT NULL,
    description TEXT,
    amount DECIMAL(10,2) NOT NULL,
    expense_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT NOW()
);

-- App Config
CREATE TABLE IF NOT EXISTS app_config (
    id BIGSERIAL PRIMARY KEY,
    pin_hash VARCHAR(255) NOT NULL,
    language VARCHAR(10) DEFAULT 'en',
    vendor_name VARCHAR(255) DEFAULT 'Sangaiya''s Aavin',
    low_stock_threshold INTEGER DEFAULT 50,
    updated_at TIMESTAMP DEFAULT NOW()
);

-- Indexes
CREATE INDEX IF NOT EXISTS idx_deliveries_date ON daily_deliveries(delivery_date);
CREATE INDEX IF NOT EXISTS idx_deliveries_customer ON daily_deliveries(customer_id);
CREATE INDEX IF NOT EXISTS idx_payments_customer ON payments(customer_id);
CREATE INDEX IF NOT EXISTS idx_payments_billing ON payments(billing_year, billing_month);
CREATE INDEX IF NOT EXISTS idx_stock_tx_date ON stock_transactions(transaction_date);

-- Seed default data
INSERT INTO app_config (pin_hash, language, vendor_name)
VALUES ('03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 'en', 'Sangaiya''s Aavin')
ON CONFLICT DO NOTHING;

INSERT INTO packet_types (name, color, color_hex, price_per_packet) VALUES
('Toned Milk',     'Blue',   '#3b82f6', 22.00),
('Standardised',   'Green',  '#22c55e', 25.00),
('Full Cream',     'Orange', '#f97316', 28.00),
('Double Toned',   'Violet', '#a855f7', 20.00),
('Homogenised',    'Brown',  '#92400e', 30.00)
ON CONFLICT DO NOTHING;

INSERT INTO suppliers (name, phone, address) VALUES
('Aavin Main Depot', '044-28550000', 'Madhavaram, Chennai'),
('Aavin Sub Depot',  '044-28551111', 'Pattaravakkam, Chennai')
ON CONFLICT DO NOTHING;
```

---

## 🚀 STEP 2 — Deploy Backend on Render

### 2a. Push to GitHub

```bash
cd aavin-backend
git init
git add .
git commit -m "Initial commit"
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/aavin-backend.git
git push -u origin main
```

### 2b. Create Render Web Service

1. Go to **https://render.com** → Sign up / Log in
2. Click **"New +"** → **"Web Service"**
3. Connect your GitHub repo → select `aavin-backend`
4. Fill in the settings:

| Field | Value |
|---|---|
| **Name** | `aavin-delivery-api` |
| **Region** | Singapore (closest to Chennai) |
| **Branch** | `main` |
| **Runtime** | `Java` |
| **Build Command** | `mvn clean package -DskipTests` |
| **Start Command** | `java -jar target/aavin-delivery-1.0.0.jar` |
| **Plan** | Free (or Starter for always-on) |

### 2c. Set Environment Variables on Render

In Render → your service → **"Environment"** tab → add these:

```
DATABASE_URL = jdbc:postgresql://ep-xxx.us-east-2.aws.neon.tech/aavindb?sslmode=require&user=your_user&password=your_pass

CORS_ALLOWED_ORIGINS = https://your-app.vercel.app,http://localhost:5173

PORT = 8080
```

> ⚠️ **Important:** The `DATABASE_URL` for Spring Boot must start with `jdbc:postgresql://` not `postgresql://`.
> Neon gives you `postgresql://...` — change it to `jdbc:postgresql://...`

### 2d. Deploy

Click **"Create Web Service"** → Render will build and deploy automatically.

Your API will be live at: `https://aavin-delivery-api.onrender.com`

---

## 🌐 STEP 3 — Deploy Frontend on Vercel

### 3a. Update Frontend API Base URL

In your React project, create `src/api/client.js`:

```javascript
const BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080';

export const api = {
  get: (path) => fetch(`${BASE_URL}${path}`).then(r => r.json()),
  post: (path, body) => fetch(`${BASE_URL}${path}`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(body),
  }).then(r => r.json()),
  put: (path, body) => fetch(`${BASE_URL}${path}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(body),
  }).then(r => r.json()),
  delete: (path) => fetch(`${BASE_URL}${path}`, { method: 'DELETE' }).then(r => r.json()),
  patch: (path, body) => fetch(`${BASE_URL}${path}`, {
    method: 'PATCH',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(body),
  }).then(r => r.json()),
};
```

Create `.env.production` in the React project root:
```
VITE_API_URL=https://aavin-delivery-api.onrender.com
```

Create `.env.development`:
```
VITE_API_URL=http://localhost:8080
```

### 3b. Push React to GitHub

```bash
cd aavin-milk-app
git init
git add .
git commit -m "Initial commit"
git remote add origin https://github.com/YOUR_USERNAME/aavin-milk-app.git
git push -u origin main
```

### 3c. Deploy on Vercel

```bash
npm install -g vercel
vercel
```

Or via Vercel dashboard:
1. Go to **https://vercel.com** → New Project
2. Import your React GitHub repo
3. Add environment variable:
   - `VITE_API_URL` = `https://aavin-delivery-api.onrender.com`
4. Deploy

### 3d. Update CORS on Render

Once Vercel gives you the URL (e.g. `https://aavin-milk-app.vercel.app`), go back to Render → Environment → update:
```
CORS_ALLOWED_ORIGINS = https://aavin-milk-app.vercel.app,http://localhost:5173
```

---

## 🔗 Complete API Reference

### Auth
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/auth/verify-pin` | Verify 4-digit PIN |
| POST | `/api/auth/change-pin` | Change PIN |
| GET | `/api/auth/config` | Get app config |
| PUT | `/api/auth/config` | Update config |

### Customers
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/customers` | All active customers (sorted by delivery order) |
| GET | `/api/customers/{id}` | Get customer by ID |
| POST | `/api/customers` | Create customer (auto-shifts delivery orders) |
| PUT | `/api/customers/{id}` | Update customer |
| DELETE | `/api/customers/{id}` | Soft delete |

### Deliveries
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/deliveries/today` | Today's delivery checklist |
| POST | `/api/deliveries/mark` | Mark a delivery as done |
| POST | `/api/deliveries/unmark/{customerId}?date=YYYY-MM-DD` | Unmark |
| PATCH | `/api/deliveries/{customerId}/packets?date=YYYY-MM-DD&packets=3` | Update packet count |
| POST | `/api/deliveries/bulk-mark-all` | Bulk mark all as delivered |
| GET | `/api/deliveries/customer/{id}?year=2026&month=5` | Customer's month calendar |

### Payments
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/payments/customer/{id}` | Payment history |
| GET | `/api/payments/paid-ids?month=5&year=2026` | IDs of paid customers |
| POST | `/api/payments` | Record payment |
| DELETE | `/api/payments/{id}` | Delete payment |

### Stock
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/stock` | All stock levels |
| GET | `/api/stock/transactions?limit=50` | Transaction history |
| POST | `/api/stock/transaction` | Purchase / Adjust / Deduct |

### Expenses
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/expenses` | All expenses |
| POST | `/api/expenses` | Add expense |
| DELETE | `/api/expenses/{id}` | Delete |

### Suppliers
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/suppliers` | All active suppliers |
| POST | `/api/suppliers` | Add supplier |
| PUT | `/api/suppliers/{id}` | Update |
| DELETE | `/api/suppliers/{id}` | Soft delete |

### Packet Types
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/packet-types` | All active types |
| POST | `/api/packet-types` | Add packet type |
| PUT | `/api/packet-types/{id}` | Update (price changes) |
| DELETE | `/api/packet-types/{id}` | Soft delete |

### Dashboard
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/dashboard/summary?from=2026-05-01&to=2026-05-31` | Full dashboard stats |

### Health
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/health` | API health check |

---

## 📱 Sample API Calls (for frontend wiring)

### Verify PIN
```json
POST /api/auth/verify-pin
{ "pin": "1234" }
```

### Mark Delivered
```json
POST /api/deliveries/mark
{
  "customerId": 1,
  "date": "2026-05-05",
  "packets": 2,
  "packetTypeId": 2,
  "substituteName": null
}
```

### Add Customer
```json
POST /api/customers
{
  "name": "Rajesh Kumar",
  "phone": "9876543210",
  "address": "12, Anna Nagar, Chennai",
  "latitude": 13.085,
  "longitude": 80.210,
  "deliveryOrder": 1,
  "defaultPackets": 2,
  "birthday": "1985-06-15",
  "notes": "Leave at gate",
  "packetConfig": {
    "type": "DAILY",
    "packetTypeId": 2,
    "count": 2
  }
}
```

### Purchase Stock
```json
POST /api/stock/transaction
{
  "packetTypeId": 1,
  "transactionType": "PURCHASE",
  "quantity": 200,
  "supplierId": 1,
  "unitCost": 18.00,
  "notes": "Morning batch"
}
```

---

## 🔒 PIN Hash Reference

Generate SHA-256 of any PIN:
```bash
echo -n "1234" | sha256sum
# 03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4
```

Default PIN is **1234**. Change it from the Settings page in the app.

---

## 🛠️ Local Development

```bash
# 1. Clone and enter project
cd aavin-backend

# 2. Set environment variables locally
export DATABASE_URL="jdbc:postgresql://localhost:5432/aavindb"
export CORS_ALLOWED_ORIGINS="http://localhost:5173"

# 3. Run
mvn spring-boot:run

# API will be at http://localhost:8080
```

---

## ✅ Deployment Checklist

- [ ] Neon DB created and SQL schema run
- [ ] Default seed data inserted (packet types, app_config)
- [ ] Backend pushed to GitHub
- [ ] Render Web Service created with correct build/start commands
- [ ] `DATABASE_URL` env var set on Render (jdbc: format!)
- [ ] `CORS_ALLOWED_ORIGINS` set with Vercel URL
- [ ] Frontend deployed on Vercel
- [ ] `VITE_API_URL` set in Vercel env vars
- [ ] Test `/api/health` endpoint works
- [ ] Test PIN verify works end-to-end
