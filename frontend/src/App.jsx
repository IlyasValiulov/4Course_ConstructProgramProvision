import { useState } from 'react';
import RegisterPage from './pages/RegisterPage';
import LoginPage from './pages/LoginPage';
import PropertiesPage from './pages/PropertiesPage';
import BookingPage from './pages/BookingPage';

export default function App() {
  const [user, setUser] = useState(null);
  const [selectedProperty, setSelectedProperty] = useState(null);
  const [screen, setScreen] = useState('login');

  if (selectedProperty && user) {
    return (
      <BookingPage
        property={selectedProperty}
        currentUser={user}
        onBack={() => setSelectedProperty(null)}
      />
    );
  }

  if (user) {
    return (
      <div>
        <div style={{ padding: '1rem 2rem', borderBottom: '1px solid #ddd', display: 'flex', justifyContent: 'space-between' }}>
          <span>Вы вошли как <strong>{user.username}</strong></span>
          <button onClick={() => { setUser(null); setScreen('login'); }}>Выйти</button>
        </div>
        <PropertiesPage
          currentUser={user}
          onSelectProperty={setSelectedProperty}
        />
      </div>
    );
  }

  return (
    <div>
      <div style={{ padding: '1rem 2rem', display: 'flex', gap: 8 }}>
        <button onClick={() => setScreen('login')}>Вход</button>
        <button onClick={() => setScreen('register')}>Регистрация</button>
      </div>
      {screen === 'register'
        ? <RegisterPage onRegistered={(u) => { setUser(u); setScreen('login'); }} />
        : <LoginPage onLoggedIn={setUser} />}
    </div>
  );
}
