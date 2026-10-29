import { useState } from 'react';
import { api } from '../api/client';

export default function BookingPage({ property, currentUser, onBack }) {
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');
  const [message, setMessage] = useState('');
  const [available, setAvailable] = useState(null);

  async function check() {
    setMessage('');
    try {
      const res = await api.checkAvailability(property.id, startDate, endDate);
      setAvailable(res.available);
      setMessage(res.available ? 'Даты доступны' : 'Даты заняты');
    } catch (err) {
      setMessage(err.message);
    }
  }

  async function book() {
    setMessage('');
    try {
      await api.createBooking({
        propertyId: property.id,
        guestId: currentUser.id,
        startDate,
        endDate,
      });
      setMessage('Бронь создана');
    } catch (err) {
      setMessage(err.message);
    }
  }

  return (
    <div style={{ padding: '2rem' }}>
      <button onClick={onBack}>← Назад</button>
      <h2>Бронирование: {property.title}</h2>
      <p>{property.city}, {property.address} — {property.pricePerNight} ₽/ночь</p>

      <div style={{ display: 'flex', gap: 8, marginBottom: 16 }}>
        <input type="date" value={startDate} onChange={e => setStartDate(e.target.value)} />
        <input type="date" value={endDate} onChange={e => setEndDate(e.target.value)} />
        <button onClick={check} disabled={!startDate || !endDate}>Проверить доступность</button>
        <button onClick={book} disabled={!startDate || !endDate || available !== true}>Забронировать</button>
      </div>

      {message && <p>{message}</p>}
    </div>
  );
}
