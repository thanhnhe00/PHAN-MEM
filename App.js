import { StatusBar } from 'expo-status-bar';
import { StyleSheet, Text, View } from 'react-native';

function Box({ color, label, textColor = '#FFFFFF', style }) {
  return (
    <View style={[styles.box, { backgroundColor: color }, style]}>
      <Text style={[styles.boxText, { color: textColor }]}>{label}</Text>
    </View>
  );
}

export default function App() {
  return (
    <View style={styles.container}>
      <StatusBar style="auto" />

      {/* Khối 6 ô: chiếm khoảng 60% màn hình */}
      <View style={styles.top}>
        <Box color="#2979F2" label="1" style={{ flex: 6 }} />
        <Box color="#F44336" label="2" style={{ flex: 6 }} />

        <View style={styles.row}>
          <Box color="#FFD61F" label="3" textColor="#000000" style={styles.small} />
          <Box color="#34A853" label="4" style={styles.small} />
          <Box color="#7E3FE0" label="5" style={styles.small} />
        </View>

        <Box color="#FF7A12" label="6" style={{ flex: 10 }} />
      </View>

      {/* Phần dưới: trống + dòng họ tên, MSSV */}
      <View style={styles.bottom}>
        <Text style={styles.info}>Họ và tên - MSSV</Text>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#FFFFFF',
    paddingTop: 48,
    paddingHorizontal: 8,
  },
  top: {
    flex: 6,
  },
  bottom: {
    flex: 4,
    alignItems: 'center',
    justifyContent: 'flex-end',
    paddingBottom: 24,
  },
  box: {
    marginBottom: 5,
    alignItems: 'center',
    justifyContent: 'center',
  },
  boxText: {
    fontSize: 36,
    fontWeight: 'bold',
  },
  row: {
    flex: 12,
    flexDirection: 'row',
  },
  small: {
    width: '24%',
    marginRight: 5,
  },
  info: {
    fontSize: 14,
    fontWeight: 'bold',
    color: '#000000',
  },
});