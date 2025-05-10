import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { FaUser } from 'react-icons/fa';
import '../styles/Navigation.css';

const Navigation = ({ isAuthenticated, setIsAuthenticated }) => {
  const [showDropdown, setShowDropdown] = useState(false);
  const navigate = useNavigate();

  const handleUserClick = () => {
    if (isAuthenticated) {
      setShowDropdown(!showDropdown);
    } else {
      navigate('/');  
    }
  };

  const handleLogout = () => {
    setIsAuthenticated(false);  // Logout user
    setShowDropdown(false);     // Hide dropdown
    navigate('/');              // Redirect to Home
  };

  return (
    <nav className="navbar">
      <div className="container">
        <div className="navbar-left">
          <Link to="/" className="logo">eTrain</Link>
        </div>
        <div className="navbar-right">
          <Link to="/" className="nav-link">Home</Link>
          {isAuthenticated && (
            <>
              <Link to="/pnr-check" className="nav-link">Check PNR</Link>
              <Link to="/train-info" className="nav-link">Train Info</Link>
              <Link to="/book-ticket" className="nav-link">Book Ticket</Link>
              <div className="user-icon-wrapper" onClick={handleUserClick}>
                <FaUser size={30} className="user-icon" />
                {showDropdown && (
                  <div className="user-dropdown">
                    <button onClick={handleLogout} className="dropdown-item">Logout</button>
                  </div>
                )}
              </div>
            </>
          )}
        </div>
      </div>
    </nav>
  );
};

export default Navigation;
