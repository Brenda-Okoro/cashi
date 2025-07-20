const express = require('express');
const https = require('https');
const fs = require('fs');
const cors = require('cors');
const { v4: uuidv4 } = require('uuid');

const app = express();
const port = 3000;

app.use(cors());
app.use(express.json());

// Mock payment processing endpoint
app.post('/payments', (req, res) => {
  const { recipientEmail, amount, currency } = req.body;

  // Basic validation
  if (!recipientEmail || !amount || !currency) {
    return res.status(400).json({
      success: false,
      message: 'Missing required fields'
    });
  }

  // Email validation
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (!emailRegex.test(recipientEmail)) {
    return res.status(400).json({
      success: false,
      message: 'Invalid email format'
    });
  }

  // Amount validation
  if (amount <= 0) {
    return res.status(400).json({
      success: false,
      message: 'Amount must be greater than 0'
    });
  }

  // Simulate processing delay
  setTimeout(() => {
    res.json({
      success: true,
      paymentId: uuidv4(),
      message: 'Payment processed successfully'
    });
  }, 1000);
});

// Create HTTPS server with self-signed certificate

try {
  const options = {
    key: fs.readFileSync('server.key'),
    cert: fs.readFileSync('server.cert')
  };

  https.createServer(options, app).listen(port, () => {
    console.log(`HTTPS Payment API server running at https://localhost:${port}`);
    console.log('For Android emulator use: https://10.0.2.2:3000');
    console.log('For physical device, use your computer\'s IP address');
  });
} catch (error) {
  console.log('HTTPS certificates not found. Run the following commands to generate them:');
  console.log('openssl req -nodes -new -x509 -keyout server.key -out server.cert -days 365');
  console.log('When prompted for Common Name, enter: localhost');
  console.log('Then restart the server.');

  // Fallback to HTTP for development
  app.listen(port, () => {
    console.log(`HTTP Payment API server running at http://localhost:${port}`);
    console.log('For Android emulator use: http://10.0.2.2:3000');
    console.log('For physical device, find your IP with ipconfig/ifconfig');
    console.log('Warning: Using HTTP - Android apps may block this connection');
  });
}
