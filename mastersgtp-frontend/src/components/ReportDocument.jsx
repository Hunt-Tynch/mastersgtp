// components/ReportDocument.jsx
import { Document, Page } from '@react-pdf/renderer';
import CrossReport from './CrossReport';
import GenericReport from './GenericReport';
import ScratchReport from './ScratchReport';
import { styles } from './styles';

const ReportDocument = ({ title, reportData, isLandscape, isScratchReport, isCrossReport, dogNumber, day }) => (
    <Document>
        <Page size={isLandscape ? [1224, 792] : [792, 1224]} style={styles.page}>
            {isScratchReport ? (
                <ScratchReport reportData={reportData} />
            ) : isCrossReport ? (
                <CrossReport  day={day} dogNumber={dogNumber} />
            ) : (
                <GenericReport reportData={reportData} />
            )}
        </Page>
    </Document>
);

export default ReportDocument;
