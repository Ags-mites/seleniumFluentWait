const express = require('express');
const cors = require('cors');

const app = express();
const PORT = 3000;

app.use(cors({
  origin: 'http://localhost:5173',
}));

// Endpoint GET /api/quote
// Simula una cotización con latencia aleatoria (3-8 segundos)
app.get('/api/quote', (req, res) => {
  const delay = Math.floor(Math.random() * (8000 - 3000 + 1)) + 3000;

  const quotes = [
    {
      status: 'success',
      total: 4500.50,
      message: 'Cotización completada'
    },
    {
      status: 'success',
      total: 3200.75,
      message: 'Oferta especial para clientes nuevos'
    },
    {
      status: 'success',
      total: 5100.00,
      message: 'Cotización premium con beneficios adicionales'
    }
  ];

  const randomQuote = quotes[Math.floor(Math.random() * quotes.length)];

  setTimeout(() => {
    res.json(randomQuote);
  }, delay);
});

app.listen(PORT, () => {
  console.log(`Servidor backend escuchando en http://localhost:${PORT}`);
});
