import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import QuoteGenerator from './QuoteGenerator'

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <QuoteGenerator />
  </StrictMode>,
)
