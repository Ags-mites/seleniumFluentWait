import { useRef, useState } from "react";

const STATUS_STEPS = [
    'Conectando al servidor...',
    'Calculando volumen...',
    'Aplicando descuentos...'
];

interface QuoteResponse {
    total: number;
    [key: string]: any;
}

export default function QuoteGenerator() {
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
            const res = await fetch('http://localhost:3001/api/quote');
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
        <div style={{ maxWidth: 400, margin: '2rem auto', padding: 24, border: '1px solid #eee', borderRadius: 12, background: '#fafbfc' }}>
            <h2>Generador de Cotización</h2>
            <button id="generate-quote-btn" onClick={handleGenerate} disabled={step !== -1} style={{ padding: '0.7em 1.5em', fontSize: 18, marginBottom: 24 }}>
                Generar Cotización
            </button>
            {step > -1 && step < STATUS_STEPS.length && (
                <div key={step} style={{ margin: '1.5em 0', fontWeight: 500, color: '#2d72d9', minHeight: 32 }}>
                    {STATUS_STEPS[step]}
                </div>
            )}
            {quote && (
                <div id="quote-status" style={{ marginTop: 24, padding: 16, background: '#e6f7e6', borderRadius: 8, color: '#1a5c1a' }}>
                    <strong>Cotización Total:</strong> ${quote.total}
                </div>
            )}
            {error && (
                <div id="quote-error" style={{ marginTop: 24, padding: 16, background: '#ffeaea', borderRadius: 8, color: '#b71c1c' }}>
                    Error: {error}
                </div>
            )}
        </div>
    );
}