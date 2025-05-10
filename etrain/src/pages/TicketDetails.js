// src/components/TicketDetails.js
import React from 'react';
import '../styles/TicketDetails.css';
import '../styles/PNRCheck.css';

function TicketDetails({ ticket }) {
  const handlePrint = () => {
    window.print();
  };

  return (
    <div className="ticket-details result-box">
      <h3>PNR Details</h3>
      <table className="pnr-table">
        <tbody>
          <tr><td>PNR Number:</td><td>{ticket.pnrNumber}</td></tr>
          <tr><td>Train Name:</td><td>{ticket.trainName}</td></tr>
          <tr><td>Journey Date:</td><td>{ticket.journeyDate}</td></tr>
          <tr><td>From:</td><td>{ticket.from}</td></tr>
          <tr><td>To:</td><td>{ticket.to}</td></tr>
          <tr><td>Booking Status:</td><td>{ticket.bookingStatus}</td></tr>
          <tr><td>Transaction ID:</td><td>{ticket.transactionId}</td></tr>
        </tbody>
      </table>

      {ticket.passengers.length > 0 && (
        <>
          <h4>Passenger Details</h4>
          <table className="pnr-table">
            <thead>
              <tr>
                <th>Name</th>
                <th>Age</th>
                <th>Gender</th>
              </tr>
            </thead>
            <tbody>
              {ticket.passengers.map((passenger, idx) => (
                <tr key={idx}>
                  <td>{passenger.name}</td>
                  <td>{passenger.age}</td>
                  <td>{passenger.gender}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </>
      )}

      <div className="print-button-container">
        <button className="print-button" onClick={handlePrint}>
        </button>
      </div>
    </div>
  );
}

export default TicketDetails;
