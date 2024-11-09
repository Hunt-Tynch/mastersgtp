import { Text, View } from '@react-pdf/renderer';
import React from 'react';
import { styles } from './styles';

const GenericReport = ({ reportData }) => (
    <View>
        {reportData.map((row, index) => (
            <View key={index} style={styles.tableRow}>
                <Text style={styles.tableCell}>{row.field1}</Text>
                <Text style={styles.tableCell}>{row.field2}</Text>
                <Text style={styles.tableCell}>{row.field3}</Text>
                <Text style={styles.tableCell}>{row.field4}</Text>
            </View>
        ))}
    </View>
);

export default GenericReport;
