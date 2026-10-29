import { useState, useEffect } from 'react';

function App() {
  const [message, setMessage] = useState('Loading...');

  useEffect(() => {
    fetch('http://localhost:8080/api/health')
      .then(res => res.text())
      .then(setMessage)
      .catch(() => setMessage('Backend not reachable'));
  }, []);

  return (
    <div style={{ padding: '2rem', fontFamily: 'sans-serif' }}>
      <h1>Сервис аренды жилья</h1>
      <p>Статус backend: <strong>{message}</strong></p>
    </div>
  );
}

export default App;
