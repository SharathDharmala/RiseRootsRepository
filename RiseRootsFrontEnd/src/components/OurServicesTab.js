import React from 'react';

const OurServicesTab = ({ setActiveTab }) => {
  return (
    <div>
      <h2>Our Services</h2>
      <p>
        RiseRoots is your gateway to a world of opportunities. We offer two core services that cater to a range of needs:
      </p>
      
      <div style={{ display: 'flex', justifyContent: 'space-between', marginTop: '20px' }}>
        <div style={{ flex: 1, paddingRight: '10px' }}>
          <h3>Real Estate</h3>
          <p>
            Whether you're looking for a place to call home or investment properties, our Real Estate section offers a curated selection of prime properties. 
            Explore locations, sizes, and prices that fit your requirements. From commercial to residential, we provide properties that meet diverse needs.
          </p>
          <button
            onClick={() => setActiveTab(1)} // Tab switch for Real Estate
            style={{
              padding: '10px 20px',
              fontSize: '16px',
              borderRadius: '5px',
              backgroundColor: '#4CAF50',
              color: 'white',
              cursor: 'pointer',
            }}
          >
            Explore Real Estate
          </button>
        </div>

        <div style={{ flex: 1, paddingLeft: '10px' }}>
          <h3>Dry Fruits</h3>
          <p>
            Our Dry Fruits section brings you the finest selection of dry fruits, including almonds, cashews, raisins, and more. 
            Order in bulk or small quantities, and enjoy the health benefits of these premium products. Our commitment is to quality and freshness.
          </p>
          <button
            onClick={() => setActiveTab(2)} // Tab switch for Dry Fruits
            style={{
              padding: '10px 20px',
              fontSize: '16px',
              borderRadius: '5px',
              backgroundColor: '#4CAF50',
              color: 'white',
              cursor: 'pointer',
            }}
          >
            Order Dry Fruits
          </button>
        </div>
      </div>
    </div>
  );
};

export default OurServicesTab;
