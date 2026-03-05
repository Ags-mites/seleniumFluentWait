import { Link } from 'react-router-dom';
import '../styles/Navbar.css';

export default function Navbar() {
  return (
    <nav className="navbar">
      <div className="navbar-container">
        <Link to="/" className="navbar-brand">
          Selenium FluentWait
        </Link>
        <ul className="nav-menu">
          <li className="nav-item">
            <Link to="/passing" className="nav-link">
              Testing correcto
            </Link>
          </li>
          <li className="nav-item">
            <Link to="/incomplete" className="nav-link">
              UI Incompleta
            </Link>
          </li>
        </ul>
      </div>
    </nav>
  );
}
