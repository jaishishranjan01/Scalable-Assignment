import React, { useState } from 'react';
import axios from 'axios';
import '../styles/TrainInfo.css';

function TrainInfo() {
  const [trains, setTrains] = useState([]);

  const fetchTrains = async () => {
    try {
      const res = await axios.get('http://localhost:8083/api/trains');
      setTrains(res.data);
    } catch (err) {
      setTrains([]);
    }
  };

  return (
    <div className="card">
      <h2 className="title">Train Information</h2>
      <button onClick={fetchTrains} className="load-button">Load Trains</button>
      <ul className="train-list">
        {trains.map((train, index) => (
          <li key={index} className="train-item">
            <strong>{train.name}</strong> - {train.route}
          </li>
        ))}
      </ul>
    </div>
  );
}

export default TrainInfo;