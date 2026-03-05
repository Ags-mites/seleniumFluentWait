import { Link } from 'react-router-dom';
import '../styles/Home.css';

export default function Home() {
  return (
    <div className="home-container">
      <div className="home-content">
        <h1>Selenium FluentWait</h1>
        <p className="subtitle">
          Escenarios de testing con Selenium WebDriver
        </p>

        <div className="scenarios-grid">
          <div className="scenario-card">
            <h3>Testing correcto</h3>
            <p>
              Escenario donde la UI cumple completamente con los requisitos del test. La cotización se genera correctamente.
            </p>
            <Link to="/passing" className="scenario-link">
              Explorar Scenario
            </Link>
          </div>

          <div className="scenario-card">
            <h3>UI Incompleta</h3>
            <p>
              Simula una UI incompleta donde faltan ciertos mensajes o elementos. El test podría fallar por elementos faltantes.
            </p>
            <Link to="/incomplete" className="scenario-link">
              Explorar Scenario
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
}
