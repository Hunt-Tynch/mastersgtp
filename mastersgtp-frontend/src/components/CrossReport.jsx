import { Text, View } from '@react-pdf/renderer';
import React, { useEffect, useState } from 'react';
import { getAllCrossForDogAndDay } from '../service/CrossService';
import { getHunt } from '../service/HuntService';
import { styles } from './styles';

const CrossReport = ({ day, dogNumber }) => {
    const [crossData, setCrossData] = useState([]);
    const [huntData, setHuntData] = useState({ name: '', dates: '', startTimes: ['', '', '', ''] });
    const [loading, setLoading] = useState(true); // Add loading state

    useEffect(() => {
        const fetchData = async () => {
            try {
                // Fetch hunt data and cross data
                const huntResponse = await getHunt();
                setHuntData(huntResponse.data);

                const crossResponse = await getAllCrossForDogAndDay(day, dogNumber);
                setCrossData(crossResponse.data);

                // Set loading to false once data is fetched
                setLoading(false);
            } catch (error) {
                console.error("Error fetching data:", error);
            }
        };

        // Only fetch data if both day and dogNumber are defined
        if (day !== undefined && dogNumber !== undefined) {
            fetchData();
        }
    }, [day, dogNumber]);

    if (loading) {
        return <Text>Loading report data...</Text>;
    }

    return (
        <View>
            <Text style={styles.title}>Individual Dog S/D Scores        Dog Number: {dogNumber}</Text>
            <View style={styles.tableHeader}>
                <Text style={{ ...styles.columnHeader, width: '12.5%' }}>Hunt: </Text><Text style={{ color: 'black', width: '12.5%', fontSize: 15 }}>{huntData.name}</Text>
                <Text style={{ ...styles.columnHeader, width: '12.5%' }}>Day: </Text><Text style={{ color: 'black', width: '12.5%', fontSize: 15 }}>{day}</Text>
                <Text style={{ ...styles.columnHeader, width: '12.5%' }}>Date: </Text><Text style={{ color: 'black', width: '12.5%', fontSize: 15 }}>{huntData.date}</Text>
                <Text style={{ ...styles.columnHeader, width: '12.5%' }}>Start Time: </Text><Text style={{ color: 'black', width: '12.5%', fontSize: 15 }}>{`${String(Math.floor(huntData.startTimes[day] / 60)).padStart(2, '0')}:${String(huntData.startTimes[day] % 60).padStart(2, '0')}`}</Text>
            </View>
            <View style={{ ...styles.tableHeader, marginTop: '15px' }}>
                <Text style={{ ...styles.columnHeader, width: '16%' }}>Time</Text>
                <Text style={{ ...styles.columnHeader, width: '16%' }}>Dog</Text>
                <Text style={{ ...styles.columnHeader, width: '16%' }}>Judge #</Text>
                <Text style={{ ...styles.columnHeader, width: '32%' }}>Judge Name</Text>
                <Text style={{ ...styles.columnHeader, width: '16%' }}>Score</Text>
                <Text style={{ ...styles.columnHeader, width: '2.67%' }}>Dup</Text>
            </View>
            {crossData.map((cross, index) => (
                <View key={index} style={styles.tableRow}>
                    <Text style={{ ...styles.tableCell, width: '16%' }}>
                        {`${String(Math.floor(cross.crossTime / 60)).padStart(2, '0')}:${String(cross.crossTime % 60).padStart(2, '0')}`}
                    </Text>
                    <Text style={{ ...styles.tableCell, width: '16%' }}>{dogNumber}</Text>
                    <Text style={{ ...styles.tableCell, width: '16%' }}>{cross.judge.number}</Text>
                    <Text style={{ ...styles.tableCell, width: '32%' }}>{cross.judge.name}</Text>
                    <Text style={{ ...styles.tableCell, width: '16%' }}>
                        {cross.dogs?.find((entry) => entry.dog.number === parseInt(dogNumber))?.points || ''}
                    </Text>
                    <Text style={{ ...styles.tableCell, width: '2.67%' }}>
                        {cross.dogs?.find((entry) => entry.dog.number === parseInt(dogNumber))?.dog.dupScores.includes(cross.id) ? 'X' : ''}
                    </Text>
                </View>
            ))}
        </View>
    );
};

export default CrossReport;
