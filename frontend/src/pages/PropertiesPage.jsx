import { useState, useEffect } from 'react';
import { api } from '../api/client';

export default function PropertiesPage({ currentUser, onSelectProperty }) {
  const [items, setItems] = useState([]);
  const [form, setForm] = useState({
    title: '',
    city: '',
    address: '',
    rooms: 1,
    pricePerNight: 1000,
  });
  const [error, setError] = useState('');

  async function load() {
    try {
      const page = await api.listProperties(0, 50);
      setItems(page.content || []);
    } catch (err) {
      setError(err.message);
    }
  }

  useEffect(() => { load(); }, []);

  async function handleCreate(e) {
    e.preventDefault();
    setError('');
    try {
      await api.createProperty({
        ...form,
        rooms: Number(form.rooms),
        pricePerNight: Number(form.pricePerNight),
        ownerId: currentUser.id,
      });
      await load();
    } catch (err) {
      setError(err.message);
    }
  }

  return (
    <div style={{ padding: '2rem' }}>
      <h2>Объекты недвижимости</h2>

      {currentUser && (
        <form onSubmit={handleCreate} style={{ marginBottom: '2rem', display: 'flex', gap: 8, flexWrap: 'wrap' }}>
          <input placeholder="Название" value={form.title}
                 onChange={e => setForm({ ...form, title: e.target.value })} required />
          <input placeholder="Город" value={form.city}
                 onChange={e => setForm({ ...form, city: e.target.value })} required />
          <input placeholder="Адрес" value={form.address}
                 onChange={e => setForm({ ...form, address: e.target.value })} required />
          <input type="number" placeholder="Комнат" value={form.rooms}
                 onChange={e => setForm({ ...form, rooms: e.target.value })} required />
          <input type="number" placeholder="Цена/ночь" value={form.pricePerNight}
                 onChange={e => setForm({ ...form, pricePerNight: e.target.value })} required />
          <button type="submit">Добавить</button>
        </form>
      )}

      {error && <p style={{ color: 'red' }}>{error}</p>}

      {items.length === 0 && <p>Пока нет объектов</p>}
      <ul>
        {items.map(p => (
          <li key={p.id} style={{ marginBottom: 8 }}>
            <strong>{p.title}</strong> — {p.city}, {p.address}, {p.rooms} комн., {p.pricePerNight} ₽/ночь{' '}
            <button onClick={() => onSelectProperty(p)}>Забронировать</button>
          </li>
        ))}
      </ul>
    </div>
  );
}
