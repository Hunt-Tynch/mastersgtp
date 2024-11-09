// components/CrossReport.jsx
import { Text, View } from '@react-pdf/renderer';
import React, { useEffect, useState } from 'react';
import { getAllCrossForDogAndDay } from '../service/CrossService';
import { getHunt } from '../service/HuntService';
import { styles } from './styles';

const CrossReport = ({ day, dogNumber }) => {
    const [crossData, setCrossData] = useState([]);
    const [huntData, setHuntData] = useState({ name: '', dates: '', startTimes: ['', '', '', '']})

    useEffect(() => {
        getHunt().then(response => {
            setHuntData(response.data)
        }).catch(error => {
            console.error("Hunt fetch failed.", error)
        })
        const fetchCrosses = async () => {
            try {
                const response = await getAllCrossForDogAndDay(day, dogNumber);
                setCrossData(response.data);
            } catch (error) {
                console.error("Error fetching crosses:", error);
            }
        };
        if (dogNumber) {
            fetchCrosses();
        }
    }, [day, dogNumber]);

    return (
        <View>
            <Text style={styles.title}>Individual Dog S/D Scores        Dog Number: {dogNumber}</Text>
            <View style={styles.tableHeader}>
                <Text style={{ ...styles.columnHeader, width: '12.5%' }}>Hunt: </Text><Text style={{color: 'black', width: '12.5%', fontSize: 15}}>{huntData.name}</Text>
                <Text style={{ ...styles.columnHeader, width: '12.5%' }}>Day: </Text><Text style={{color: 'black', width: '12.5%', fontSize: 15}}>{day}</Text>
                <Text style={{ ...styles.columnHeader, width: '12.5%' }}>Date: </Text><Text style={{color: 'black', width: '12.5%', fontSize: 15}}>{huntData.date}</Text>
                <Text style={{ ...styles.columnHeader, width: '12.5%' }}>Start Time: </Text><Text style={{color: 'black', width: '12.5%', fontSize: 15}}>{`${String(Math.floor(huntData.startTimes[day] / 60)).padStart(2, '0')}:${String(huntData.startTimes[day] % 60).padStart(2, '0')}`}</Text>
            </View>
            <View style={{ ...styles.tableHeader, marginTop: '15px' }}>
                <Text style={{ ...styles.columnHeader, width: '16.67%' }}>Time</Text>
                <Text style={{ ...styles.columnHeader, width: '16.67%' }}>Dog</Text>
                <Text style={{ ...styles.columnHeader, width: '16.67%' }}>Judge #</Text>
                <Text style={{ ...styles.columnHeader, width: '32%' }}>Judge Name</Text>
                <Text style={{ ...styles.columnHeader, width: '16.67%' }}>Score</Text>
            </View>
            {crossData.map((cross, index) => (
                <View key={index} style={styles.tableRow}>
                    <Text style={{ ...styles.tableCell, width: '16.67%' }}>
                        {`${String(Math.floor(cross.crossTime / 60)).padStart(2, '0')}:${String(cross.crossTime % 60).padStart(2, '0')}`}
                    </Text>
                    <Text style={{ ...styles.tableCell, width: '16.67%' }}>{dogNumber}</Text>
                    <Text style={{ ...styles.tableCell, width: '16.67%' }}>{cross.judge.number}</Text>
                    <Text style={{ ...styles.tableCell, width: '32%' }}>{cross.judge.name}</Text>
                    <Text style={{ ...styles.tableCell, width: '16.67%' }}>
                        {35 - (cross.dogs.findIndex(d => d.number === Number(dogNumber)) * 5)}
                    </Text>
                </View>
            ))}
        </View>
    );
};

export default CrossReport;
