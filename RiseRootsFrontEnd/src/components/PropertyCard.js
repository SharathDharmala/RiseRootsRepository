// src/components/PropertyCard.js
import React from 'react';
import { Carousel } from 'react-responsive-carousel';

const PropertyCard = ({ property }) => {
  return (
    <div style={{ marginBottom: '40px' }}>
      <h4>{property.name}</h4>
      <p>{property.description}</p>

      <Carousel
        autoPlay
        infiniteLoop
        showArrows={true}
        showThumbs={false}
        interval={3000} // Auto-slide interval
        dynamicHeight={false}
        emulateTouch={true}
        swipeable={true}
      >
        {property.images.map((image, idx) => (
          <div key={idx}>
            <img src={process.env.PUBLIC_URL + image} alt={`property-image-${idx}`} />
          </div>
        ))}
      </Carousel>

      <button
        style={{
          padding: '10px 20px',
          borderRadius: '5px',
          backgroundColor: '#4CAF50',
          color: 'white',
          cursor: 'pointer',
          marginTop: '20px',
        }}
      >
        Contact Us for More Info
      </button>
    </div>
  );
};

export default PropertyCard;
