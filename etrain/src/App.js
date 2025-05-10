import React, { useState } from 'react';
import { BrowserRouter as Router, Route, Routes, useLocation } from 'react-router-dom';
import Navigation from './pages/Navigation';
import Home from './pages/Home';
import PNRCheck from './pages/PNRCheck';
import TrainInfo from './pages/TrainInfo';
import Signup from './pages/Signup';
import Login from './pages/Login';
import BookTicket from './pages/BookTicket';
import './styles/App.css';

function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  return (
    <Router>
      <Navigation isAuthenticated={isAuthenticated} setIsAuthenticated={setIsAuthenticated} />
      <MainContent isAuthenticated={isAuthenticated} setIsAuthenticated={setIsAuthenticated} />
    </Router>
  );
}

function MainContent({ isAuthenticated, setIsAuthenticated }) {
  const location = useLocation();

  return (
    <main>
      <div className="main-section">
        <div className="content-wrapper">
          {location.pathname === '/' ? (
            isAuthenticated ? (
              <Home />
            ) : (
              <>
                <div className="left-image">
                </div>
                <div className='form-side-right'>
                  <div className="form-side">
                    <Login setIsAuthenticated={setIsAuthenticated} />
                  </div>
                </div>
              </>
            )
          ) : (
            <div className="page-content">
              <Routes>
                <Route path="/" element={<Home key={location.pathname} />} />
                <Route path="/book-ticket" element={<BookTicket key={location.pathname} />} />
                <Route path="/pnr-check" element={<PNRCheck key={location.pathname} />} />
                <Route path="/train-info" element={<TrainInfo key={location.pathname} />} />
                <Route path="/signup" element={<Signup key={location.pathname} />} />
              </Routes>
            </div>
          )}
        </div>
      </div>
    </main>
  );
}

export default App;
