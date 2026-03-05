import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
import './index.css'
import Layout from './components/Layout'
import Home from './pages/Home'
import QuoteGeneratorPassing from './pages/QuoteGeneratorPassing'
import QuoteGeneratorIncomplete from './pages/QuoteGeneratorIncomplete'

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <Router>
      <Layout>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/passing" element={<QuoteGeneratorPassing />} />
          <Route path="/incomplete" element={<QuoteGeneratorIncomplete />} />
        </Routes>
      </Layout>
    </Router>
  </StrictMode>,
)
