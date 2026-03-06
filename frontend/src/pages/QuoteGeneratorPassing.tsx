import { useRef, useState } from "react";
import '../styles/QuoteGenerator.css';

const STATUS_STEPS = [
    'Conectando al servidor...',
    'Calculando volumen...',
    'Aplicando descuentos...'
];

interface QuoteResponse {
    total: number;
    [key: string]: any;
}

export default function QuoteGeneratorPassing() {
    const [step, setStep] = useState<number>(-1);
    const [quote, setQuote] = useState<QuoteResponse | null>(null);
    const [error, setError] = useState<string | null>(null);
    const intervalRef = useRef<number | null>(null);

    const handleGenerate = async () => {
        setQuote(null);
        setError(null);
        setStep(0);
        let currentStep = 0;
        intervalRef.current = window.setInterval(() => {
            setStep(-1);
            window.setTimeout(() => {
                currentStep++;
                if (currentStep < STATUS_STEPS.length) {
                    setStep(currentStep);
                } else {
                    if (intervalRef.current) window.clearInterval(intervalRef.current);
                    fetchQuote();
                }
            }, 50);
        }, 1200);
    };

    const fetchQuote = async () => {
        try {
            const res = await fetch('http://localhost:3000/api/quote');
            if (!res.ok) throw new Error('Error en la respuesta del servidor');
            const data = await res.json();
            setQuote(data);
        } catch (e: any) {
            setError(e.message);
        } finally {
            setStep(-1);
        }
    };

    return (
        <div className="quote-generator-container">
            <div className="quote-card">
                <h2>Generador de Cotización</h2>
                <p className="scenario-description">UI completa y correctamente estructurada</p>
                
                <button 
                    id="generate-quote-btn" 
                    onClick={handleGenerate} 
                    disabled={step !== -1}
                    className="btn-generate"
                >
                    Generar Cotización
                </button>

                {step > -1 && step < STATUS_STEPS.length && (
                    <div className="status-message" key={step}>
                        {STATUS_STEPS[step]}
                    </div>
                )}

                {quote && (
                    <div id="quote-status" className="quote-result success">
                        <strong>Cotización Total:</strong> ${quote.total}
                    </div>
                )}

                {error && (
                    <div id="quote-error" className="quote-result error">
                        Error: {error}
                    </div>
                )}
            </div>
        </div>
    );
}
