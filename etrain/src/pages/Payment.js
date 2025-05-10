import React, { useState } from 'react';
import '../styles/Payment.css';
import axios from 'axios';

function Payment({ paymentMethod, setPaymentMethod, onConfirm, onCancel }) {
  const [upiId, setUpiId] = useState('');
  const [cardDetails, setCardDetails] = useState({
    name: '',
    number: '',
    expiryMonth: '',
    expiryYear: '',
    cvv: ''
  });
  const [errors, setErrors] = useState({});
  const [isProcessing, setIsProcessing] = useState(false);

  const validateName = (name) => /^[A-Za-z\s]+$/.test(name.trim());
  const validateCardNumber = (number) => /^\d{12,16}$/.test(number);
  const validateCvv = (cvv) => /^\d{3}$/.test(cvv);

  const handleCardDetailChange = (field, value) => {
    let updatedDetails = { ...cardDetails, [field]: value };
    setCardDetails(updatedDetails);

    let newErrors = { ...errors };

    if (field === 'name' && value && !validateName(value)) {
      newErrors.name = 'Only letters and spaces allowed.';
    } else if (field === 'name') {
      delete newErrors.name;
    }

    if (field === 'number') {
      const numOnly = value.replace(/\D/g, '');
      updatedDetails.number = numOnly;
      if (!validateCardNumber(numOnly)) {
        newErrors.number = 'Card number must be 12-16 digits.';
      } else {
        delete newErrors.number;
      }
    }

    if (field === 'cvv') {
      const cvvOnly = value.replace(/\D/g, '');
      updatedDetails.cvv = cvvOnly;
      if (!validateCvv(cvvOnly)) {
        newErrors.cvv = 'CVV must be exactly 3 digits.';
      } else {
        delete newErrors.cvv;
      }
    }

    setErrors(newErrors);
    setCardDetails(updatedDetails);
  };

  const handleConfirmPayment = async () => {
    const newErrors = {};
  
    if (paymentMethod === 'Card') {
      if (!validateName(cardDetails.name)) {
        newErrors.name = 'Only letters and spaces allowed.';
      }
      if (!validateCardNumber(cardDetails.number)) {
        newErrors.number = 'Card number must be 12-16 digits.';
      }
      if (!validateCvv(cardDetails.cvv)) {
        newErrors.cvv = 'CVV must be exactly 3 digits.';
      }
    }
  
    if (paymentMethod === 'UPI' && !upiId.trim()) {
      newErrors.upi = 'UPI ID is required.';
    }
  
    setErrors(newErrors);
  
    if (Object.keys(newErrors).length > 0) return;
  
    setIsProcessing(true);
  
    try {
      const token = localStorage.getItem('token'); // Optional: replace with real token logic
  
      if (paymentMethod === 'UPI') {
        await axios.post(
          'http://localhost:8083/payment/api/upi',
          { upiId, totalAmount: 999 }, // Replace 999 with actual amount if needed
          { headers: {  Authorization: `Bearer ${token}` } }
        );
      } else if (paymentMethod === 'Card') {
        await axios.post(
          'http://localhost:8083/payment/api/card',
          {
            cardNumber: cardDetails.number,
            expiryMonth: cardDetails.expiryMonth,
            expiryYear: cardDetails.expiryYear,
            cvv: cardDetails.cvv,
            cardHolderName: cardDetails.name,
            totalAmount: 999 // Replace with actual amount
          },
          { headers: {  Authorization: `Bearer ${token}`} }
        );
      }
  
      onConfirm(); // Call success callback
    } catch (error) {
      console.error("Payment error:", error);
      alert("Payment failed. Please try again.");
    } finally {
      setIsProcessing(false);
    }
  };
  

  return (
    <div className="modal-overlay">
      <div className="modal-content">
        {isProcessing ? (
          <div className="processing-payment">
            <div className="spinner"></div>
            <p>Processing Payment... Please wait</p>
          </div>
        ) : (
          <>
            <h3 className="modal-title">Select Payment Method</h3>

            <div className="payment-options">
              <label>
                <input
                  type="radio"
                  value="UPI"
                  checked={paymentMethod === 'UPI'}
                  onChange={(e) => setPaymentMethod(e.target.value)}
                />
                UPI
              </label>

              <label>
                <input
                  type="radio"
                  value="Card"
                  checked={paymentMethod === 'Card'}
                  onChange={(e) => setPaymentMethod(e.target.value)}
                />
                Card
              </label>
            </div>

            {paymentMethod === 'UPI' && (
              <div className="upi-section">
                <input
                  type="text"
                  placeholder="Enter UPI ID"
                  className="payment-input"
                  value={upiId}
                  onChange={(e) => {
                    setUpiId(e.target.value);
                    setErrors({});
                  }}
                />
                {errors.upi && <div className="error-message">{errors.upi}</div>}
              </div>
            )}

            {paymentMethod === 'Card' && (
              <div className="card-section">
                <input
                  type="text"
                  placeholder="Card Holder Name"
                  className="payment-input"
                  value={cardDetails.name}
                  onChange={(e) => handleCardDetailChange('name', e.target.value)}
                />
                {errors.name && <div className="error-message">{errors.name}</div>}

                <input
                  type="text"
                  placeholder="Card Number"
                  className="payment-input"
                  value={cardDetails.number}
                  onChange={(e) => handleCardDetailChange('number', e.target.value)}
                  maxLength={16}
                />
                {errors.number && <div className="error-message">{errors.number}</div>}

                <div className="expiry-cvv">
                    <div className="expiry-selects">
                        <select
                        className="payment-input half-width"
                        value={cardDetails.expiryMonth}
                        onChange={(e) => handleCardDetailChange('expiryMonth', e.target.value)}
                        >
                        <option value="">Month</option>
                        {Array.from({ length: 12 }, (_, i) => (
                            <option key={i + 1} value={String(i + 1).padStart(2, '0')}>
                            {String(i + 1).padStart(2, '0')}
                            </option>
                        ))}
                        </select>

                        <select
                        className="payment-input half-width"
                        value={cardDetails.expiryYear}
                        onChange={(e) => handleCardDetailChange('expiryYear', e.target.value)}
                        >
                        <option value="">Year</option>
                        {Array.from({ length: 15 }, (_, i) => {
                            const year = new Date().getFullYear() + i;
                            return (
                            <option key={year} value={year}>
                                {year}
                            </option>
                            );
                        })}
                        </select>
                    </div>
                  <input
                    type="text"
                    placeholder="CVV"
                    className="payment-input half-width"
                    value={cardDetails.cvv}
                    onChange={(e) => handleCardDetailChange('cvv', e.target.value)}
                    maxLength={3}
                  />
                </div>

                {errors.cvv && <div className="error-message">{errors.cvv}</div>}
              </div>
            )}

            <div className="modal-buttons">
              <button onClick={handleConfirmPayment} className="confirm-button">
                Confirm Payment
              </button>
              <button onClick={onCancel} className="cancel-button">
                Cancel
              </button>
            </div>
          </>
        )}
      </div>
    </div>
  );
}

export default Payment;
