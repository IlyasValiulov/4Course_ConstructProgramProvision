const BASE = 'http://localhost:8080/api';

async function request(path, options = {}) {
  const res = await fetch(BASE + path, {
    headers: { 'Content-Type': 'application/json' },
    ...options,
  });
  if (!res.ok) {
    const text = await res.text();
    throw new Error(`${res.status}: ${text}`);
  }
  return res.json();
}

export const api = {
  register: (username, password) =>
    request('/auth/register', {
      method: 'POST',
      body: JSON.stringify({ username, password }),
    }),

  login: (username, password) =>
    request('/auth/login', {
      method: 'POST',
      body: JSON.stringify({ username, password }),
    }),

  listProperties: (page = 0, size = 20) =>
    request(`/properties?page=${page}&size=${size}`),

  createProperty: (data) =>
    request('/properties', { method: 'POST', body: JSON.stringify(data) }),

  getProperty: (id) => request(`/properties/${id}`),

  checkAvailability: (propertyId, startDate, endDate) =>
    request(`/bookings/availability?propertyId=${propertyId}&startDate=${startDate}&endDate=${endDate}`),

  createBooking: (data) =>
    request('/bookings', { method: 'POST', body: JSON.stringify(data) }),
};
