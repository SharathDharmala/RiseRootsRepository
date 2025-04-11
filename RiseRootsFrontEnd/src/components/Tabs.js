// src/components/Tabs.js
import React from 'react';

const Tabs = ({ tabs, activeTab, setActiveTab }) => {
  return (
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
  );
};

export default Tabs;
