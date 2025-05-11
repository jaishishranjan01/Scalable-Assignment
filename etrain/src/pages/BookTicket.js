import React, { useState } from 'react';
import axios from 'axios';
import Payment from './Payment';
import TicketDetails from './TicketDetails';
import { useNavigate } from 'react-router-dom';
import '../styles/BookTicket.css';
import { useEffect } from 'react';


function BookTicket() {
  const [sourceQuery, setSourceQuery] = useState('');
  const [destinationQuery, setDestinationQuery] = useState('');
  const [sourceStations, setSourceStations] = useState([]);
  const [destinationStations, setDestinationStations] = useState([]);
  const [selectedSourceStation, setSelectedSourceStation] = useState(null);
  const [selectedDestinationStation, setSelectedDestinationStation] = useState(null);
  const [searchResults, setSearchResults] = useState([]);
  const [selectedTrainId, setSelectedTrainId] = useState(null);
  const [passengers, setPassengers] = useState([{ name: '', age: '', gender: '' }]);
  const [paymentModalOpen, setPaymentModalOpen] = useState(false);
  const [paymentMethod, setPaymentMethod] = useState('UPI');
  const [ticketResult, setTicketResult] = useState(null); // store booked ticket here
  const navigate = useNavigate();
  const baseURL = window._env_.REACT_APP_TRAIN_SERVICE_URL;

  const fetchStations = async (query, setStations) => {
    if (!query.trim()) {
      setStations([]);
      return;
    }
    try {
      const res = await axios.get(`${window._env_.REACT_APP_TRAIN_SERVICE_URL}/api/trains/stations/search?query=${query}`);
      setStations(res.data);
    } catch (error) {
      console.error('Failed to fetch stations:', error);
    }
  };

  const handleSearch = async () => {
    if (!selectedSourceStation || !selectedDestinationStation) {
      alert("Please select both source and destination stations!");
      return;
    }
    try {
      const token = localStorage.getItem('token');
      
      const res = await fetch(baseURL + '/api/trains/search', {
        method: 'POST',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          sourceStation: selectedSourceStation.id,
          destinationStation: selectedDestinationStation.id
        })
      });
      if (!res.ok) throw new Error('Failed to search trains.');
      const data = await res.json();
      setSearchResults(data);
    } catch (error) {
      console.error(error);
      alert('Failed to search trains.');
    }
  };

  useEffect(() => {
    // Reset form state on component mount (route change)
    setSourceQuery('');
    setDestinationQuery('');
    setSourceStations([]);
    setDestinationStations([]);
    setSelectedSourceStation(false);
    setSelectedDestinationStation(false);
    setSearchResults([]);
    setSelectedTrainId(null);
    setPassengers([{ name: '', age: '', gender: '' }]);
    setPaymentModalOpen(false);
    setPaymentMethod('UPI');
    setTicketResult(null);
  }, []);
  

  const handlePassengerChange = (index, field, value) => {
    const updatedPassengers = [...passengers];
    updatedPassengers[index][field] = value;
    setPassengers(updatedPassengers);
  };

  const addPassengerRow = () => {
    setPassengers([...passengers, { name: '', age: '', gender: '' }]);
  };

  const openPaymentModal = () => {
    setPaymentModalOpen(true);
  };

  const handlePaymentConfirm = async () => {
    try {
      const token = localStorage.getItem('token');
  
      if (!token) {
        alert('You are not logged in!');
        return;
      }
  
      // Simulate Payment Random Success/Failure
      const isPaymentSuccess = Math.random() < 0.7;
  
      if (!isPaymentSuccess) {
        setPaymentModalOpen(false);
        alert("Payment Failed! Cannot book ticket.");
        navigate("/book-ticket");
        return;
      }
  
      // Payment Successful → Now Book
      const res = await fetch(baseURL + '/api/trains/book', {
        method: 'POST',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          trainId: selectedTrainId,
          passengers,
          paymentSuccess: true
        })
      });
  
      if (!res.ok) {
        throw new Error('Failed to book ticket.');
      }
  
      const bookingData = await res.json();
  
      //Now after booking, fetch the ticket details using PNR
      const ticketRes = await axios.get(`${window._env_.REACT_APP_TRAIN_SERVICE_URL}/pnr/${bookingData.pnrNumber}`, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
  
      const ticket = ticketRes.data;
  
      // Map ticket for TicketDetails page
      setTicketResult({
        pnrNumber: ticket.pnrNumber,
        trainName: ticket.trainName,
        journeyDate: ticket.journeyDate,
        from: ticket.sourceStation.stationName || ticket.sourceStation,
        to: ticket.destinationStation.stationName || ticket.destinationStation,
        passengers: ticket.passengers || [],
        transactionId: ticket.transactionId,
        bookingStatus: ticket.bookingStatus
      });
  
      setPaymentModalOpen(false);
  
    } catch (error) {
      console.error(error);
      alert('Failed to book or fetch ticket.');
    }
  };
  

  const handlePaymentCancel = () => {
    setPaymentModalOpen(false);
  };

  if (ticketResult) {
    return (
      <div className="ticket-details-page result-box">
        <h2 className="heading">Booking Successful!</h2>
        <TicketDetails ticket={ticketResult} />
      </div>
    );
  }

  return (
    <div className="booking-container">
      <h2 className="heading">Book Train Ticket</h2>

      {/* Search Section */}
      <div className="search-section">
        {/* Source Station */}
        <div className="search-box">
          <input
            type="text"
            value={sourceQuery}
            onChange={(e) => {
              setSourceQuery(e.target.value);
              fetchStations(e.target.value, setSourceStations);
            }}
            placeholder="Type Source Station"
            className="passenger-input"
          />
          {sourceStations.length > 0 && (
            <ul className="dropdown-list">
              {sourceStations.map((station) => (
                <li
                  key={station.id}
                  onClick={() => {
                    setSelectedSourceStation(station);
                    setSourceQuery(station.stationName);
                    setSourceStations([]);
                  }}
                  className="dropdown-item"
                >
                  {station.stationName} ({station.stationCode})
                </li>
              ))}
            </ul>
          )}
        </div>

        {/* Destination Station */}
        <div className="search-box">
          <input
            type="text"
            value={destinationQuery}
            onChange={(e) => {
              setDestinationQuery(e.target.value);
              fetchStations(e.target.value, setDestinationStations);
            }}
            placeholder="Type Destination Station"
            className="passenger-input"
          />
          {destinationStations.length > 0 && (
            <ul className="dropdown-list">
              {destinationStations.map((station) => (
                <li
                  key={station.id}
                  onClick={() => {
                    setSelectedDestinationStation(station);
                    setDestinationQuery(station.stationName);
                    setDestinationStations([]);
                  }}
                  className="dropdown-item"
                >
                  {station.stationName} ({station.stationCode})
                </li>
              ))}
            </ul>
          )}
        </div>

        <button onClick={handleSearch} className="search-button">
          Search
        </button>
      </div>

      {/* Train List */}
      {searchResults.length > 0 && (
        <div className="train-list">
          <h3 className="text-xl font-semibold mb-4">Available Trains</h3>
          <ul className="space-y-2">
            {searchResults.map((train) => (
              <li key={train.id} className="train-item">
                <span>{train.trainName} - {train.departureTime} ➔ {train.arrivalTime}</span>
                <button
                  onClick={() => setSelectedTrainId(train.id)}
                  className="select-train-button"
                >
                  Select
                </button>
              </li>
            ))}
          </ul>
        </div>
      )}

      {/* Passenger Section */}
      {selectedTrainId && (
        <div className="passenger-section">
          <h3 className="text-lg font-semibold mb-4">Passenger Details</h3>

          {passengers.map((passenger, index) => (
            <div key={index} className="passenger-row">
              <input
                type="text"
                placeholder="Name"
                value={passenger.name}
                onChange={(e) => handlePassengerChange(index, 'name', e.target.value)}
                className="passenger-input-inline"
              />
              <input
                type="number"
                placeholder="Age"
                value={passenger.age}
                onChange={(e) => handlePassengerChange(index, 'age', e.target.value)}
                className="passenger-input-inline"
              />
              <select
                value={passenger.gender}
                onChange={(e) => handlePassengerChange(index, 'gender', e.target.value)}
                className="passenger-input-inline"
              >
                <option value="">Gender</option>
                <option value="Male">Male</option>
                <option value="Female">Female</option>
                <option value="Other">Other</option>
              </select>

              {index === passengers.length - 1 && (
                <button onClick={addPassengerRow} className="add-passenger-button-inline">
                  + Add
                </button>
              )}
            </div>
          ))}

          <button onClick={openPaymentModal} className="book-button">
            Book Now
          </button>
        </div>
      )}

      {/* Payment Modal */}
      {paymentModalOpen && (
        <Payment
          paymentMethod={paymentMethod}
          setPaymentMethod={setPaymentMethod}
          onConfirm={handlePaymentConfirm}
          onCancel={handlePaymentCancel}
        />
      )}
    </div>
  );
}

export default BookTicket;
