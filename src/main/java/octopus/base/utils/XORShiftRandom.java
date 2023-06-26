package octopus.base.utils;

/** XORShift 난수생성기 */
public class XORShiftRandom {
    
    private long last;
    
    public XORShiftRandom() {
        this(System.currentTimeMillis());
    }
    
    public XORShiftRandom(long seed) {
        this.last = seed;
    }
    
    public int nextInt(int max) {
        last ^= (last << 21);
        last ^= (last >>> 35);
        last ^= (last << 4);
        int out = (int) last % max;
        return (out < 0) ? -out : out;
    }
}