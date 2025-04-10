import React, { useState, useEffect } from 'react';

function App() {
  const [selectedItem, setSelectedItem] = useState('');
  const [quantity, setQuantity] = useState('');
  const [orderList, setOrderList] = useState([]);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [activeTab, setActiveTab] = useState(0);
  const [items, setItems] = useState([]);
  const [successMessage, setSuccessMessage] = useState('');

  const tabs = [
    { name: 'Real Estate', content: 'Explore our available properties.' },
    { name: 'Dry Fruits Order', content: 'Order your favorite dry fruits.' }
  ];

  useEffect(() => {
    const fetchItems = async () => {
      try {
        const response = await fetch("http://localhost:8074/api/products/dfproducts");
        const data = await response.json();
        console.log(data);
        setItems(data);
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
    const newOrder = {
      productId: selectedProduct?.productId,
      productName: selectedProduct?.productName,
      quantity,
      remarks: selectedProduct?.remarks,
      price_per_gram_inr: selectedProduct?.price_per_gram_inr
    };

    setOrderList([...orderList, newOrder]);
    setSelectedItem('');
    setQuantity('');
  };

  const handleSubmitOrder = async () => {
    try {
      setIsSubmitting(true);

      console.log("Submitting order:", orderList);

      const orderRequest = {
        orderType: "IN",
        orderedProducts: orderList.map(order => ({
          productId: order.productId,
          quantity: order.quantity,
          remarks: order.remarks,
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
      console.log("Order submitted:", data);
      setOrderList([]);
      setSuccessMessage("✅ Order has been submitted successfully!");
      setTimeout(() => setSuccessMessage(''), 4000);
    } catch (error) {
      console.error("Error submitting order:", error);
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div
      style={{
        backgroundImage: `url(${process.env.PUBLIC_URL + '/background.png'})`,
        backgroundSize: 'cover',
        backgroundPosition: 'center',
        width: '100vw',
        minHeight: '100vh',
        color: 'romanticblue',
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
      }}
    >
      <header
        style={{
          backgroundImage: `url(${process.env.PUBLIC_URL + '/logo.png'})`,
          backgroundSize: 'cover',
          backgroundPosition: 'center',
          height: '30vh',
          display: 'flex',
          justifyContent: 'flex-start',
          alignItems: 'center',
          paddingLeft: '8%',
          color: 'white',
          textAlign: 'left',
          width: '100%',
        }}
      >
        <div className="header-content">
          <div className="header-title">RiseRoots</div>
          <div className="header-tagline">Grounded in Excellence, Rising with Innovation</div>
        </div>
      </header>

      <main
        style={{
          width: '80%',
          backgroundColor: 'rgba(255, 255, 255, 0.8)',
          padding: '20px',
          borderRadius: '10px',
          margin: '20px 0',
          boxShadow: '0 0 10px rgba(0, 0, 0, 0.5)',
        }}
      >
        <div className="tabs">
          {tabs.map((tab, index) => (
            <button
              key={index}
              className={activeTab === index ? 'active-tab' : ''}
              onClick={() => setActiveTab(index)}
              style={{
                padding: '10px 20px',
                margin: '0 10px',
                fontSize: '16px',
                borderRadius: '5px',
                border: '1px solid #ccc',
                backgroundColor: activeTab === index ? '#4CAF50' : '#f1f1f1',
                color: activeTab === index ? 'white' : '#333',
                cursor: 'pointer',
              }}
            >
              {tab.name}
            </button>
          ))}
        </div>

        <div className="tab-content">
          <h2>{tabs[activeTab].name}</h2>
          <p>{tabs[activeTab].content}</p>

          {activeTab === 1 && (
            <>
              <div className="order-section">
                <select
                  value={selectedItem}
                  onChange={(e) => setSelectedItem(e.target.value)}
                  style={{
                    padding: '10px',
                    marginRight: '10px',
                    borderRadius: '5px',
                    border: '1px solid #ccc',
                    fontSize: '16px',
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
                    marginRight: '10px',
                    borderRadius: '5px',
                    border: '1px solid #ccc',
                    fontSize: '16px',
                  }}
                />

                <button
                  onClick={handleAddItem}
                  style={{
                    padding: '10px 20px',
                    borderRadius: '5px',
                    backgroundColor: '#4CAF50',
                    color: 'white',
                    cursor: 'pointer',
                  }}
                >
                  Add Item
                </button>
              </div>

              {selectedItem && (
                <div style={{ marginTop: '20px' }}>
                  <h3>Item Description:</h3>
                  <p>
                    Remarks: {items.find(item => item.productName === selectedItem)?.remarks} — ₹
                    {items.find(item => item.productName === selectedItem)?.price_per_gram_inr}
                  </p>
                </div>
              )}

              {successMessage && (
                <div style={{ marginTop: '15px', color: 'green', fontWeight: 'bold' }}>
                  {successMessage}
                </div>
              )}

              <ul style={{ listStyleType: 'none', padding: '0' }}>
                {orderList.map((order, index) => (
                  <li key={index} style={{ margin: '5px 0', fontSize: '16px' }}>
                    <strong>{order.productName}</strong> - {order.quantity} grams
                    <br />
                    <small>Remarks: {order.remarks}, ₹{order.price_per_gram_inr}/gm</small>
                  </li>
                ))}
              </ul>

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
                  marginTop: '20px',
                }}
              >
                {isSubmitting ? "Submitting..." : "Submit Order"}
              </button>
            </>
          )}

          {activeTab === 0 && (
            <div style={{ fontSize: '18px', paddingTop: '20px' }}>
              <p>Explore our available real estate properties:</p>
              <ul>
                <li>Property 1: Location, Size, Price</li>
                <li>Property 2: Location, Size, Price</li>
                <li>Property 3: Location, Size, Price</li>
              </ul>
            </div>
          )}
        </div>
      </main>
    </div>
  );
}

export default App;
