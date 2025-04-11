import React, { useState, useEffect } from 'react';
import OurServicesTab from './components/OurServicesTab';  // New component
import RealEstateTab from './components/RealEstateTab';    // New component
import DryFruitsTab from './components/DryFruitsTab';      // New component
import BackgroundImage from './components/BodyBackgroundImage'; // Import the background image component
import ImageGallery from './components/ImageGallery'; // Import the new ImageGallery component

const App = () => {
  const [activeTab, setActiveTab] = useState(0); // Default active tab to "Our Services"
  const [items, setItems] = useState([]);
  const [realEstateCategories, setRealEstateCategories] = useState([]);

  const tabs = [
    { name: 'Our Services', content: 'Explore our services including Real Estate and Dry Fruits.' },
    { name: 'Real Estate', content: 'Explore our available real estate properties.' },
    { name: 'Dry Fruits', content: 'Order your favorite dry fruits.' },
  ];

  // Fetch items and real estate categories from the API
  useEffect(() => {
    const fetchItems = async () => {
      try {
        const response = await fetch("http://localhost:8074/api/products/dfproducts");
        const data = await response.json();
        setItems(data);
      } catch (error) {
        console.error("Error fetching items:", error);
      }
    };

    const fetchRealEstateCategories = async () => {
      try {
        const response = await fetch('/config/properties.json');
        const jsonData = await response.json();
        setRealEstateCategories(jsonData);
      } catch (error) {
        console.error('Error loading JSON file:', error);
      }
    };

    fetchItems();
    fetchRealEstateCategories();
  }, []);

  return (
    <div
      style={{
        position: 'relative',
        minHeight: '100vh',
        color: 'darkblue',
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
      }}
    >
      {/* Background Image */}
      <BackgroundImage />

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
          zIndex: 1,
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
          zIndex: 2,
          height: '60vh', // Limit the height of the main content panel
          overflowY: 'auto', // Allow vertical scrolling only in the content area
        }}
      >
        <div
          className="tabs"
          style={{
            display: 'flex',
            marginBottom: '20px',
            background: 'linear-gradient(to right, #f1f1f1, #e0e0e0)',
            borderRadius: '10px',
            boxShadow: '0 2px 8px rgba(0, 0, 0, 0.2)',
          }}
        >
          {tabs.map((tab, index) => (
            <button
              key={index}
              className={activeTab === index ? 'active-tab' : ''}
              onClick={() => setActiveTab(index)}
              style={{
                padding: '12px 25px',
                margin: '0 5px',
                fontSize: '16px',
                fontWeight: '600',
                borderRadius: activeTab === index ? '10px 10px 0 0' : '5px',
                border: 'none',
                backgroundColor: activeTab === index ? '#ffffff' : '#f1f1f1',
                color: activeTab === index ? '#4CAF50' : '#333',
                cursor: 'pointer',
                boxShadow: activeTab === index ? '0 4px 10px rgba(0, 0, 0, 0.2)' : 'none',
                transition: 'background-color 0.3s ease, transform 0.3s ease, box-shadow 0.3s ease',
                textAlign: 'center',
                borderBottom: activeTab === index ? '3px solid #4CAF50' : 'none',
              }}
            >
              {tab.name}
            </button>
          ))}
        </div>

        <div className="tab-content">
          {activeTab === 0 && <OurServicesTab />}
          {activeTab === 1 && <RealEstateTab categories={realEstateCategories} />}
          {activeTab === 2 && <DryFruitsTab items={items} />}
        </div>

        <ImageGallery images={['/images/image1.jpg', '/images/image2.jpg', '/images/image3.jpg']} />
      </main>
    </div>
  );
};

export default App;
