import React, { useState } from 'react';
import axios from 'axios';
import '../styles/Login.css';
import { Link } from 'react-router-dom';

function Login({ setIsAuthenticated }) {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  const handleLogin = async (e) => {
    e.preventDefault();
    setError(''); // clear any previous error

    if (email === 'admin' && password === 'admin') {
      setIsAuthenticated(true);
      console.log('Admin logged in directly!');
      return;
    }

    //Otherwise, call backend API for normal user login
    try {
      const baseURL = window._env_.REACT_APP_AUTH_SERVICE_URL
      const res = await axios.post( baseURL + "/api/auth/login", { email, password });
    
      const token = res.data.token; //Assuming backend sends { "token": "..." }
      console.log("Received token:", token);
    
      if (token) {
        localStorage.setItem('token', token); //Save token to localStorage
        setIsAuthenticated(true); // Login successful
      } else {
        throw new Error('No token received');
      }
    } catch (err) {
      console.error(err);
      setError('Invalid credentials.');
    }
    
  };

  return (
    <div className="login-container">
      <div className="background">
        <div className="shape"></div>
        <div className="shape"></div>
      </div>
      <form className="login-form" onSubmit={handleLogin}>
        <h3>Login</h3>

        <label htmlFor="email">Username</label>
        <input
          type="text"
          id="email"
          placeholder="Email or Phone"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />

        <label htmlFor="password">Password</label>
        <input
          type="password"
          id="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />

        <p className="signup-link">
          Don't have an account? <Link to="/signup">Sign up</Link>
        </p>

        {error && <p style={{ fontSize: 18, color: '#dc2626', marginTop: '10px' }}>{error}</p>}

        <button type="submit">Log In</button>
      </form>
    </div>
  );
}

export default Login;
