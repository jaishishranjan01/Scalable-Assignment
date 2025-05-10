import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom'; // for redirect after signup
import '../styles/Signup.css';

function Signup() {
  const [formData, setFormData] = useState({
    name: '',
    age: '',
    mobile: '',
    email: '',
    address: '',
    password: '',
    confirmPassword: '',
  });

  const [error, setError] = useState('');
  const navigate = useNavigate(); // used for redirecting to login page after signup

  const handleChange = (e) => {
    const { id, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [id]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');

    if (formData.password !== formData.confirmPassword) {
      setError('Passwords do not match!');
      return;
    }

    try {
      const signupData = {
        name: formData.name,
        age: formData.age,
        mobile: formData.mobile,
        email: formData.email,
        address: formData.address,
        password: formData.password,
      };

      const response = await axios.post('http://localhost:8081/api/auth/signup', signupData);
      console.log(response.data);
      alert('Signup Successful! Please login now.');
      navigate('/'); // Redirect to Home/Login page after signup
    } catch (err) {
      console.error(err);
      if (err.response && err.response.data && err.response.data.message) {
        setError(err.response.data.message);
      } else {
        setError('Signup failed. Please try again.');
      }
    }
  };

  return (
    <div className="signup-container">
      <form className="signup-form" onSubmit={handleSubmit}>
        <h3>Create Account</h3>

        <label htmlFor="name">Name</label>
        <input id="name" type="text" value={formData.name} onChange={handleChange} placeholder="Enter your name" required />

        <label htmlFor="age">Age</label>
        <input id="age" type="number" value={formData.age} onChange={handleChange} placeholder="Enter your age" required />

        <label htmlFor="mobile">Mobile</label>
        <input id="mobile" type="text" value={formData.mobile} onChange={handleChange} placeholder="Enter your mobile number" required />

        <label htmlFor="email">Email</label>
        <input id="email" type="email" value={formData.email} onChange={handleChange} placeholder="Enter your email" required />

        <label htmlFor="address">Address</label>
        <input id="address" type="text" value={formData.address} onChange={handleChange} placeholder="Enter your address" required />

        <label htmlFor="password">Password</label>
        <input id="password" type="password" value={formData.password} onChange={handleChange} placeholder="Enter password" required />

        <label htmlFor="confirmPassword">Confirm Password</label>
        <input id="confirmPassword" type="password" value={formData.confirmPassword} onChange={handleChange} placeholder="Confirm password" required />

        {error && <p className="error-text">{error}</p>}

        <button type="submit">Register</button>
      </form>
    </div>
  );
}

export default Signup;
