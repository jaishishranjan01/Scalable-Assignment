import React, { useState } from 'react';
import axios from 'axios';
import TicketDetails from './TicketDetails';
import '../styles/PNRCheck.css';
import '../styles/App.css';

function PNRCheck() {
  const [pnr, setPnr] = useState('');
  const [result, setResult] = useState(null);
  const [error, setError] = useState('');

  const handleCheck = async () => {
    try {
      const token = localStorage.getItem('token');
      if (!token) {
        alert('You must be logged in to check PNR.');
        return;
      }

      const res = await axios.get(`http://localhost:8082/api/trains/pnr/${pnr}`, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });

      const ticket = res.data;
      const mappedResult = {
        pnrNumber: ticket.pnrNumber,
        trainName: ticket.trainName,
        journeyDate: ticket.journeyDate,
        from: ticket.sourceStation.stationName || ticket.sourceStation,
        to: ticket.destinationStation.stationName || ticket.destinationStation,
        passengers: ticket.passengers || [],
        transactionId: ticket.transactionId,
        bookingStatus: ticket.bookingStatus
      };

      setResult(mappedResult);
      setError('');

    } catch (error) {
      console.error(error);
      setError('PNR not found or service unavailable.');
      setResult(null);
    }
  };

  return (
    <div className="pnr-check-container">
      <div className="card">
        <h2 className="title">PNR Check</h2>
        <div className="form-row">
          <input 
            type="text" 
            value={pnr} 
            onChange={(e) => setPnr(e.target.value)} 
            placeholder="Enter PNR Number" 
            className="input-field" 
          />
          <button onClick={handleCheck} className="check-button">Check</button>
        </div>

        {error && (
          <div className="error-text">{error}</div>
        )}

        {result && (
          <div className="result-box">
            <TicketDetails ticket={result} />
          </div>
        )}
      </div>
    </div>
  );
}

export default PNRCheck;
