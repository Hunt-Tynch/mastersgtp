import { Text, View } from '@react-pdf/renderer';
import React from 'react';
import { styles } from './styles';

const ScratchReport = ({ reportData }) => (
    <View>
        <View style={styles.tableHeader}>
            <Text style={{ ...styles.columnHeader, width: '20%' }}>Dog Number</Text>
            <Text style={{ ...styles.columnHeader, width: '30%' }}>Judge Name</Text>
            <Text style={{ ...styles.columnHeader, width: '30%' }}>Reason</Text>
            <Text style={{ ...styles.columnHeader, width: '20%' }}>Time</Text>
        </View>
        {reportData.map((scratch, index) => (
            <View key={index} style={styles.tableRow}>
                <Text style={{ ...styles.tableCell, width: '20%' }}>{scratch.dog.number}</Text>
                <Text style={{ ...styles.tableCell, width: '30%' }}>{scratch.judge.name}</Text>
                <Text style={{ ...styles.tableCell, width: '30%' }}>{scratch.reason}</Text>
                <Text style={{ ...styles.tableCell, width: '20%' }}>
                    {`${String(Math.floor(scratch.time / 60)).padStart(2, '0')}:${String(scratch.time % 60).padStart(2, '0')}`}
                </Text>
            </View>
        ))}
    </View>
);

export default ScratchReport;
