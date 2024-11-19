import { PDFDownloadLink, PDFViewer } from '@react-pdf/renderer';
import React, { useState } from 'react';
import { getScratches } from '../service/ScratchService';
import ReportDocument from './ReportDocument';

const SampleReportPage = () => {
    const [selectedReport, setSelectedReport] = useState(null);
    const [isLandscape, setIsLandscape] = useState(false);
    const [scratchData, setScratchData] = useState([]);

    const reportDataSamples = [
        { field1: 'Data 1A', field2: 'Data 1B', field3: 'Data 1C', field4: 'Data 1D' },
        { field1: 'Data 2A', field2: 'Data 2B', field3: 'Data 2C', field4: 'Data 2D' },
    ];

    const reports = Array.from({ length: 8 }, (_, i) => ({
        title: `Report ${i + 1}`,
        data: reportDataSamples,
        isScratchReport: false,
    }));

    const fetchScratchData = async () => {
        try {
            const response = await getScratches();
            setScratchData(response.data);
            setSelectedReport({
                title: 'Scratch Report',
                data: response.data,
                isScratchReport: true,
            });
        } catch (error) {
            console.error('Error fetching scratches:', error);
        }
    };

    const handleViewReport = (report) => {
        setSelectedReport(report);
    };

    const handleCrossReportView = () => {
        const day = prompt('Please enter the Day for the Cross Report:');
        const dogNumber = prompt('Please enter the Dog Number for the Cross Report:');
        if (dogNumber) {
            setSelectedReport({
                title: `Cross Report for Dog #${dogNumber}`,
                isCrossReport: true,
                day,
                dogNumber,
            });
        }
    };

    return (
        <div className="p-6 bg-gray-100 rounded-lg shadow-lg max-w-4xl mx-auto text-black">
            <h2 className="text-2xl font-bold text-center mb-6">Generate Reports</h2>

            {/* Buttons for Reports */}
            <div className="grid grid-cols-2 md:grid-cols-3 gap-4 mb-6">
                {reports.map((report, index) => (
                    <button
                        key={index}
                        className="bg-blue-500 text-white font-semibold py-2 px-4 rounded hover:bg-blue-600 transition"
                        onClick={() => handleViewReport(report)}
                    >
                        View {report.title}
                    </button>
                ))}
                <button
                    className="bg-blue-500 text-white font-semibold py-2 px-4 rounded hover:bg-blue-600 transition"
                    onClick={handleCrossReportView}
                >
                    Cross Report
                </button>
                <button
                    className="bg-blue-500 text-white font-semibold py-2 px-4 rounded hover:bg-blue-600 transition"
                    onClick={fetchScratchData}
                >
                    Scratch Report
                </button>
            </div>

            {/* Report Viewer */}
            {selectedReport && (
                <>
                    <div className="text-center mb-3">
                        <button
                            className="bg-gray-500 text-white font-semibold py-2 px-4 rounded hover:bg-gray-600 transition"
                            onClick={() => setIsLandscape(!isLandscape)}
                        >
                            Orientation: {isLandscape ? 'Landscape' : 'Portrait'}
                        </button>
                    </div>

                    <h3 className="text-center text-xl font-semibold mb-3">{selectedReport.title}</h3>

                    <PDFViewer
                        style={{
                            width: '100%',
                            height: '500px',
                            border: '1px solid #ddd',
                            marginBottom: '20px',
                        }}
                    >
                        <ReportDocument
                            title={selectedReport.title}
                            reportData={selectedReport.isScratchReport ? scratchData : selectedReport.data}
                            isLandscape={isLandscape}
                            isScratchReport={selectedReport.isScratchReport}
                            isCrossReport={selectedReport.isCrossReport}
                            dogNumber={selectedReport.dogNumber}
                            day={selectedReport.day}
                        />
                    </PDFViewer>

                    <div className="text-center mt-4">
                        <PDFDownloadLink
                            document={
                                <ReportDocument
                                    title={selectedReport.title}
                                    reportData={selectedReport.isScratchReport ? scratchData : selectedReport.data}
                                    isLandscape={isLandscape}
                                    isScratchReport={selectedReport.isScratchReport}
                                    isCrossReport={selectedReport.isCrossReport}
                                    dogNumber={selectedReport.dogNumber}
                                />
                            }
                            fileName={`${selectedReport.title.toLowerCase().replace(/\s+/g, '_')}.pdf`}
                        >
                            {({ loading }) => (
                                <button className="bg-green-500 text-white font-semibold py-2 px-4 rounded hover:bg-green-600 transition">
                                    {loading ? 'Preparing download...' : 'Download PDF'}
                                </button>
                            )}
                        </PDFDownloadLink>
                    </div>
                </>
            )}
        </div>
    );
};

export default SampleReportPage;
