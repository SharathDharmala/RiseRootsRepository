import React, { useState, useEffect } from 'react';

const DryFruits = () => {
  const [selectedItem, setSelectedItem] = useState('');
  const [quantity, setQuantity] = useState('');
  const [orderList, setOrderList] = useState([]);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [items, setItems] = useState([]); // Array to hold product objects
  const [showModal, setShowModal] = useState(false); // State to control modal visibility
  const [successMessage, setSuccessMessage] = useState('Order submitted successfully!');
  const [showSuccessPopup, setShowSuccessPopup] = useState(false); // State to control popup visibility

  useEffect(() => {
    const fetchItems = async () => {
      try {
        const response = await fetch("http://localhost:8074/api/products/dfproducts");
        const data = await response.json();
        console.log('Fetched items:', data);
        setItems(data); // Set the product objects into state
      } catch (error) {
        console.error("Error fetching items:", error);
      }
    };

    fetchItems();
  }, []);

  const handleAddItem = () => {
    if (!selectedItem || !quantity) {
      alert("Please select an item and enter a quantity.");
      return;
    }

    const selectedProduct = items.find(item => item.productName === selectedItem);
    if (!selectedProduct) {
      alert("Selected item is invalid.");
      return;
    }

    const updatedOrderList = [...orderList];
    const existingItemIndex = updatedOrderList.findIndex(order => order.productName === selectedItem);

    if (existingItemIndex !== -1) {
      // If the item already exists, update the quantity
      updatedOrderList[existingItemIndex].quantity = (
        parseInt(updatedOrderList[existingItemIndex].quantity) + parseInt(quantity)
      ).toString();
    } else {
      // If the item doesn't exist, add it to the list
      const newOrder = {
        productId: selectedProduct?.productId,
        productName: selectedProduct?.productName,
        quantity,
        price_per_gram_inr: selectedProduct?.price_per_gram_inr,
      };
      updatedOrderList.push(newOrder);
    }

    setOrderList(updatedOrderList);
    setSelectedItem('');
    setQuantity('');
  };

  const handleSubmitOrder = async () => {
    try {
      setIsSubmitting(true);
      const orderRequest = {
        orderType: "IN",
        orderedProducts: orderList.map(order => ({
          productId: order.productId,
          quantity: order.quantity,
          quantity_price_inr: order.price_per_gram_inr * order.quantity
        }))
      };

      const response = await fetch("http://localhost:8074/api/orders/dfsubmit", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(orderRequest)
      });

      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }

      const data = await response.json();
      setSuccessMessage("✅ Order has been submitted successfully!");
      setShowSuccessPopup(true);

    } catch (error) {
      console.error("Error submitting order:", error);
    } finally {
      setIsSubmitting(false);
    }
  };

  const handleModalClick = () => {
    setShowSuccessPopup(false);
    setOrderList([]); // Clear the order list to start a new order
  };

  const handleDeleteItem = (productName) => {
    const updatedOrderList = orderList.filter(order => order.productName !== productName);
    setOrderList(updatedOrderList);
  };

  return (
    <div style={{ marginTop: '20px', display: 'flex', justifyContent: 'space-between', gap: '20px' }}>
      {/* Left Panel - Input Fields */}
      <div style={{ width: '45%', padding: '20px', borderRadius: '10px', backgroundColor: '#f8f8f8', boxShadow: '0 0 15px rgba(0, 0, 0, 0.1)' }}>
        <h3>Select Your Dry Fruit Order</h3>

        <select
          value={selectedItem}
          onChange={(e) => setSelectedItem(e.target.value)}
          style={{
            padding: '10px',
            marginBottom: '20px',
            borderRadius: '5px',
            border: '1px solid #ddd',
            fontSize: '16px',
            width: '100%',
            backgroundColor: '#fff',
            boxShadow: '0 0 5px rgba(0, 0, 0, 0.1)',
          }}
        >
          <option value="">Select Item</option>
          {items.map((item, index) => (
            <option key={index} value={item.productName}>
              {item.productName}
            </option>
          ))}
        </select>

        <input
          type="number"
          value={quantity}
          onChange={(e) => setQuantity(e.target.value)}
          placeholder="Quantity in grams"
          style={{
            padding: '10px',
            marginBottom: '20px',
            borderRadius: '5px',
            border: '1px solid #ddd',
            fontSize: '16px',
            width: '100%',
            backgroundColor: '#fff',
            boxShadow: '0 0 5px rgba(0, 0, 0, 0.1)',
          }}
        />

        <button
          onClick={handleAddItem}
          style={{
            padding: '10px 20px',
            borderRadius: '5px',
            backgroundColor: '#2196F3',
            color: 'white',
            cursor: 'pointer',
            fontSize: '16px',
            width: '100%',
            border: 'none',
            boxShadow: '0 0 5px rgba(0, 0, 0, 0.1)',
            transition: 'background-color 0.3s ease',
          }}
          onMouseOver={(e) => e.target.style.backgroundColor = '#1e88e5'}
          onMouseOut={(e) => e.target.style.backgroundColor = '#2196F3'}
        >
          Add Item
        </button>
      </div>

      {/* Right Panel - Table with Order List */}
      <div style={{ width: '50%', padding: '20px', borderRadius: '10px', backgroundColor: '#f8f8f8', boxShadow: '0 0 15px rgba(0, 0, 0, 0.1)' }}>
        <h3>Your Order Summary</h3>

        <table style={{
          width: '100%',
          borderCollapse: 'collapse',
          borderRadius: '10px',
          boxShadow: '0 0 10px rgba(0, 0, 0, 0.1)',
          overflow: 'hidden',
          marginBottom: '20px'
        }}>
          <thead>
            <tr style={{ backgroundColor: '#2196F3', color: 'white' }}>
              <th style={{ padding: '12px', textAlign: 'left' }}>Product Name</th>
              <th style={{ padding: '12px', textAlign: 'left' }}>Quantity (grams)</th>
              <th style={{ padding: '12px', textAlign: 'left' }}>Price per Gram (INR)</th>
              <th style={{ padding: '12px', textAlign: 'left' }}>Actions</th>
            </tr>
          </thead>
          <tbody>
            {orderList.length > 0 ? (
              orderList.map((order, index) => (
                <tr key={index} style={{ backgroundColor: index % 2 === 0 ? '#f9f9f9' : '#fff' }}>
                  <td style={{ padding: '12px', borderBottom: '1px solid #ddd' }}>{order.productName}</td>
                  <td style={{ padding: '12px', borderBottom: '1px solid #ddd' }}>{order.quantity}</td>
                  <td style={{ padding: '12px', borderBottom: '1px solid #ddd' }}>
                    ₹{(order.price_per_gram_inr * order.quantity).toFixed(2)}
                  </td>
                  <td style={{ padding: '12px', borderBottom: '1px solid #ddd' }}>
                    <button
                      onClick={() => handleDeleteItem(order.productName)}
                      style={{
                        backgroundColor: 'red',
                        color: 'white',
                        padding: '6px 12px',
                        borderRadius: '5px',
                        border: 'none',
                        cursor: 'pointer',
                      }}
                    >
                      Delete
                    </button>
                  </td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan="4" style={{ padding: '12px', textAlign: 'center', color: '#888' }}>
                  No items in the order.
                </td>
              </tr>
            )}
          </tbody>
        </table>

        <button
          onClick={handleSubmitOrder}
          disabled={orderList.length === 0 || isSubmitting}
          style={{
            padding: '10px 20px',
            borderRadius: '5px',
            backgroundColor: isSubmitting ? '#999' : '#4CAF50',
            color: 'white',
            cursor: isSubmitting ? 'not-allowed' : 'pointer',
            fontSize: '16px',
            width: '100%',
            border: 'none',
            transition: 'background-color 0.3s ease',
          }}
        >
          {isSubmitting ? "Submitting..." : "Submit Order"}
        </button>
      </div>

      {/* Success Modal */}
      {showSuccessPopup && (
        <div
          style={{
            position: 'fixed',
            top: '50%',
            left: '50%',
            transform: 'translate(-50%, -50%)',
            backgroundColor: '#fff',
            padding: '30px',
            borderRadius: '15px',
            boxShadow: '0 0 15px rgba(0, 0, 0, 0.3)',
            zIndex: '1000',
            textAlign: 'center',
            cursor: 'pointer',
            width: '80%',
            maxWidth: '500px',
          }}
          onClick={handleModalClick}
        >
          <h3>{successMessage}</h3>
          <h4>Order Summary:</h4>

          <table style={{
            width: '100%',
            borderCollapse: 'collapse',
            marginTop: '10px',
            borderRadius: '10px',
            boxShadow: '0 0 10px rgba(0, 0, 0, 0.1)',
            overflow: 'hidden',
          }}>
            <thead>
              <tr style={{ backgroundColor: '#2196F3', color: 'white' }}>
                <th style={{ padding: '12px', textAlign: 'left' }}>Product Name</th>
                <th style={{ padding: '12px', textAlign: 'left' }}>Quantity (grams)</th>
                <th style={{ padding: '12px', textAlign: 'left' }}>Price per Gram (INR)</th>
              </tr>
            </thead>
            <tbody>
              {orderList.length > 0 ? (
                orderList.map((order, index) => (
                  <tr key={index} style={{ backgroundColor: index % 2 === 0 ? '#f9f9f9' : '#fff' }}>
                    <td style={{ padding: '12px', borderBottom: '1px solid #ddd' }}>{order.productName}</td>
                    <td style={{ padding: '12px', borderBottom: '1px solid #ddd' }}>{order.quantity}</td>
                    <td style={{ padding: '12px', borderBottom: '1px solid #ddd' }}>
                      ₹{(order.price_per_gram_inr * order.quantity).toFixed(2)}
                    </td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="3" style={{ padding: '12px', textAlign: 'center', color: '#888' }}>
                    No items in the order.
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
};

export default DryFruits;
