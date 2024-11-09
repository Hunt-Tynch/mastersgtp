import { Font, StyleSheet } from '@react-pdf/renderer';
import TimesNewRoman from '../font/times-new-roman-bold-italic.ttf';

Font.register({
    family: 'Times New Roman',
    src: TimesNewRoman,
    fontStyle: 'bold italic'
});

export const styles = StyleSheet.create({
    page: {
        padding: 30,
        flexDirection: 'column',
        justifyContent: 'space-between',
    },
    title: {
        fontSize: 24,
        marginBottom: 10,
        color: 'blue',
        fontFamily: 'Times New Roman',
        fontStyle: 'bold italic',
    },
    tableHeader: {
        flexDirection: 'row',
        borderBottom: '1px solid black',
        paddingBottom: 4,
        marginBottom: 4,
        color: 'blue',
        fontFamily: 'Times New Roman',
        fontStyle: 'bold italic',
    },
    tableRow: {
        flexDirection: 'row',
        marginBottom: 4,
    },
    columnHeader: {
        fontWeight: 'bold',
        width: '25%',
    },
    tableCell: {
        width: '25%',
    },
    footer: {
        borderTop: '2px solid gray',
        position: 'absolute',
        bottom: 30,
        left: 20,
        right: 0,
        paddingTop: 5,
        textAlign: 'start',
        fontSize: 10,
        color: 'blue',
        maxWidth: '95vw',
        fontFamily: 'Times New Roman',
        fontStyle: 'bold italic',
    },
});
