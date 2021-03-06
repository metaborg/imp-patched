package org.eclipse.imp.presentation.parser;

public class PSPLexerprs implements lpg.runtime.ParseTable, PSPLexersym {
    public final static int ERROR_SYMBOL = 0;
    public final int getErrorSymbol() { return ERROR_SYMBOL; }

    public final static int SCOPE_UBOUND = 0;
    public final int getScopeUbound() { return SCOPE_UBOUND; }

    public final static int SCOPE_SIZE = 0;
    public final int getScopeSize() { return SCOPE_SIZE; }

    public final static int MAX_NAME_LENGTH = 0;
    public final int getMaxNameLength() { return MAX_NAME_LENGTH; }

    public final static int NUM_STATES = 14;
    public final int getNumStates() { return NUM_STATES; }

    public final static int NT_OFFSET = 102;
    public final int getNtOffset() { return NT_OFFSET; }

    public final static int LA_STATE_OFFSET = 896;
    public final int getLaStateOffset() { return LA_STATE_OFFSET; }

    public final static int MAX_LA = 1;
    public final int getMaxLa() { return MAX_LA; }

    public final static int NUM_RULES = 274;
    public final int getNumRules() { return NUM_RULES; }

    public final static int NUM_NONTERMINALS = 47;
    public final int getNumNonterminals() { return NUM_NONTERMINALS; }

    public final static int NUM_SYMBOLS = 149;
    public final int getNumSymbols() { return NUM_SYMBOLS; }

    public final static int SEGMENT_SIZE = 8192;
    public final int getSegmentSize() { return SEGMENT_SIZE; }

    public final static int START_STATE = 275;
    public final int getStartState() { return START_STATE; }

    public final static int IDENTIFIER_SYMBOL = 0;
    public final int getIdentifier_SYMBOL() { return IDENTIFIER_SYMBOL; }

    public final static int EOFT_SYMBOL = 100;
    public final int getEoftSymbol() { return EOFT_SYMBOL; }

    public final static int EOLT_SYMBOL = 103;
    public final int getEoltSymbol() { return EOLT_SYMBOL; }

    public final static int ACCEPT_ACTION = 621;
    public final int getAcceptAction() { return ACCEPT_ACTION; }

    public final static int ERROR_ACTION = 622;
    public final int getErrorAction() { return ERROR_ACTION; }

    public final static boolean BACKTRACK = false;
    public final boolean getBacktrack() { return BACKTRACK; }

    public final int getStartSymbol() { return lhs(0); }
    public final boolean isValidForParser() { return PSPLexersym.isValidForParser; }


    public interface IsNullable {
        public final static byte isNullable[] = {0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0
        };
    };
    public final static byte isNullable[] = IsNullable.isNullable;
    public final boolean isNullable(int index) { return isNullable[index] != 0; }

    public interface ProsthesesIndex {
        public final static byte prosthesesIndex[] = {0,
            12,13,21,22,23,24,25,26,27,28,
            29,30,31,32,33,34,35,36,37,38,
            39,40,41,42,43,44,45,46,14,10,
            15,19,20,2,3,4,5,6,7,8,
            9,11,16,17,18,47,1
        };
    };
    public final static byte prosthesesIndex[] = ProsthesesIndex.prosthesesIndex;
    public final int prosthesesIndex(int index) { return prosthesesIndex[index]; }

    public interface IsKeyword {
        public final static byte isKeyword[] = {0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0
        };
    };
    public final static byte isKeyword[] = IsKeyword.isKeyword;
    public final boolean isKeyword(int index) { return isKeyword[index] != 0; }

    public interface BaseCheck {
        public final static byte baseCheck[] = {0,
            1,1,1,1,1,1,1,1,1,1,
            1,1,1,1,1,1,1,2,1,5,
            1,2,3,1,1,1,1,1,1,1,
            1,1,2,2,2,1,2,1,2,2,
            2,3,1,2,1,1,1,1,1,1,
            1,1,1,1,1,1,1,1,1,1,
            1,1,1,1,1,1,1,1,1,1,
            1,1,1,1,1,1,1,1,1,1,
            1,1,1,1,1,1,1,1,1,1,
            1,1,1,1,1,1,1,1,1,1,
            1,1,1,1,1,1,1,1,1,1,
            1,1,1,1,1,1,1,1,1,1,
            1,1,1,1,1,1,1,1,1,1,
            1,1,1,1,1,1,1,1,1,1,
            1,1,1,1,1,1,1,1,1,1,
            1,1,1,1,1,1,1,1,1,1,
            1,1,1,1,1,1,1,1,1,1,
            1,1,1,1,1,1,1,1,1,1,
            1,1,1,1,1,1,1,1,1,1,
            1,1,1,1,1,1,1,1,1,1,
            1,1,1,1,1,1,1,1,1,1,
            1,1,1,1,1,1,1,1,1,1,
            1,1,1,1,1,1,1,1,1,1,
            1,1,1,1,1,1,1,1,1,1,
            1,1,1,1,1,1,1,1,1,1,
            1,1,1,1,1,1,1,1,1,1,
            1,1,1,1,1,1,1,1,1,1,
            1,1,1,1
        };
    };
    public final static byte baseCheck[] = BaseCheck.baseCheck;
    public final int baseCheck(int index) { return baseCheck[index]; }
    public final static byte rhs[] = baseCheck;
    public final int rhs(int index) { return rhs[index]; };

    public interface BaseAction {
        public final static char baseAction[] = {
            34,34,34,34,34,34,34,34,34,34,
            34,34,34,34,34,34,34,34,34,34,
            40,41,41,41,30,30,30,30,42,42,
            42,42,35,35,35,35,36,36,37,37,
            38,38,39,45,45,32,32,32,32,32,
            32,1,1,1,1,1,1,1,1,1,
            1,3,3,4,4,5,5,6,6,7,
            7,8,8,9,9,10,10,11,11,12,
            12,13,13,14,14,15,15,16,16,17,
            17,18,18,19,19,20,20,21,21,22,
            22,23,23,24,24,25,25,26,26,27,
            27,28,28,2,2,2,2,2,2,2,
            2,2,2,2,2,2,2,2,2,2,
            2,2,2,2,2,2,2,2,2,29,
            29,29,29,29,46,46,46,46,46,46,
            46,46,46,46,46,46,46,46,46,46,
            46,46,46,46,46,46,46,46,46,46,
            46,46,46,46,46,46,33,33,33,33,
            33,33,33,33,33,33,33,33,33,33,
            33,33,33,33,33,33,33,33,33,33,
            33,33,33,33,33,33,33,31,31,31,
            31,31,31,31,31,31,31,31,31,31,
            31,31,31,31,31,31,31,31,31,31,
            31,31,31,31,31,31,31,31,43,43,
            43,43,43,43,43,43,43,43,43,43,
            43,43,43,43,43,43,43,43,43,43,
            43,43,43,43,43,43,43,43,43,44,
            44,44,44,44,44,595,36,32,113,114,
            115,116,117,118,119,120,121,122,123,124,
            125,126,127,128,129,130,131,132,133,134,
            135,136,137,138,38,750,37,578,747,307,
            561,305,590,480,5,19,201,24,25,113,
            114,115,116,117,118,119,120,121,122,123,
            124,125,126,127,128,129,130,131,132,133,
            134,135,136,137,138,26,21,27,680,762,
            622,622,622,622,622,622,622,404,497,46,
            45,113,114,115,116,117,118,119,120,121,
            122,123,124,125,126,127,128,129,130,131,
            132,133,134,135,136,137,138,622,622,622,
            43,47,622,622,622,622,622,622,622,622,
            622,622,622,527,1,24,25,113,114,115,
            116,117,118,119,120,121,122,123,124,125,
            126,127,128,129,130,131,132,133,134,135,
            136,137,138,26,22,27,101,28,29,113,
            114,115,116,117,118,119,120,121,122,123,
            124,125,126,127,128,129,130,131,132,133,
            134,135,136,137,138,30,622,622,622,622,
            622,622,622,622,622,622,622,622,23,31,
            301,270,269,113,114,115,116,117,118,119,
            120,121,122,123,124,125,126,127,128,129,
            130,131,132,133,134,135,136,137,138,622,
            622,622,622,622,622,622,622,622,622,622,
            622,622,622,622,41,622,271,399,46,45,
            113,114,115,116,117,118,119,120,121,122,
            123,124,125,126,127,128,129,130,131,132,
            133,134,135,136,137,138,622,622,622,44,
            47,682,34,33,113,114,115,116,117,118,
            119,120,121,122,123,124,125,126,127,128,
            129,130,131,132,133,134,135,136,137,138,
            761,622,622,622,622,622,622,622,622,622,
            622,622,622,622,622,622,622,622,622,622,
            622,622,622,622,622,622,622,622,622,39,
            622,622
        };
    };
    public final static char baseAction[] = BaseAction.baseAction;
    public final int baseAction(int index) { return baseAction[index]; }
    public final static char lhs[] = baseAction;
    public final int lhs(int index) { return lhs[index]; };

    public interface TermCheck {
        public final static byte termCheck[] = {0,
            0,1,2,3,4,5,6,7,8,9,
            10,11,12,13,14,15,16,17,18,19,
            20,21,22,23,24,25,26,27,28,29,
            30,31,32,33,34,35,36,37,38,39,
            40,41,42,43,44,45,46,47,48,49,
            50,51,52,53,54,55,56,57,58,59,
            60,61,62,63,64,65,66,67,68,69,
            70,71,72,73,74,75,76,77,78,79,
            80,81,82,83,84,85,86,87,88,89,
            90,91,92,93,94,95,96,97,98,99,
            0,1,2,3,4,5,6,7,8,9,
            10,11,12,13,14,15,16,17,18,19,
            20,21,22,23,24,25,26,27,28,29,
            30,31,32,33,34,35,36,37,38,39,
            40,41,42,43,44,45,46,47,48,49,
            50,51,52,53,54,55,56,57,58,59,
            60,61,62,63,64,65,66,67,68,69,
            70,71,72,73,74,75,76,77,78,79,
            80,81,82,83,84,85,86,87,88,89,
            90,91,92,93,94,95,96,97,98,99,
            0,1,2,3,4,5,6,7,8,9,
            10,11,12,13,14,15,16,17,18,19,
            20,21,22,23,24,25,26,27,28,29,
            30,31,32,33,34,35,36,37,38,39,
            40,41,42,43,44,45,46,47,48,49,
            50,51,52,53,54,55,56,57,58,59,
            60,61,62,63,64,65,66,67,0,69,
            70,71,72,73,74,75,76,77,78,79,
            80,81,82,83,84,85,86,87,88,89,
            90,91,92,93,94,95,96,97,98,99,
            0,1,2,3,4,5,6,7,8,9,
            10,11,12,13,14,15,16,17,18,19,
            20,21,22,23,24,25,26,27,28,29,
            30,31,32,33,34,35,36,37,38,39,
            40,41,42,43,44,45,46,47,48,49,
            50,51,52,53,54,55,56,57,58,59,
            60,61,62,63,64,65,66,67,68,69,
            70,71,72,73,74,75,76,77,78,79,
            80,81,82,83,84,85,86,87,88,89,
            90,91,92,93,94,95,96,97,0,1,
            2,3,4,5,6,7,8,9,10,11,
            12,13,14,15,16,17,18,19,20,21,
            22,23,24,25,26,27,28,29,30,31,
            32,33,34,35,36,37,38,39,40,41,
            42,43,44,45,46,47,48,49,50,51,
            52,53,54,55,56,57,58,59,60,61,
            62,63,64,65,66,67,68,69,70,71,
            72,73,74,75,76,77,78,79,80,81,
            82,83,84,85,86,87,88,89,90,91,
            92,93,94,95,96,97,0,1,2,3,
            4,5,6,7,8,9,10,11,12,13,
            14,15,16,17,18,19,20,21,22,23,
            24,25,26,27,28,29,30,31,32,33,
            34,35,36,37,38,39,40,41,42,43,
            44,45,46,47,48,49,50,51,52,53,
            54,55,56,57,58,59,60,61,62,63,
            64,65,66,67,68,69,70,71,72,73,
            74,75,76,77,78,79,80,0,82,83,
            84,85,86,87,88,89,90,91,92,93,
            94,95,96,97,0,1,2,3,4,5,
            6,7,8,9,10,11,12,13,14,15,
            16,17,18,19,20,21,22,23,24,25,
            26,27,28,29,30,31,32,33,34,35,
            36,37,38,39,40,41,42,43,44,45,
            46,47,48,49,50,51,52,53,54,55,
            56,57,58,59,60,61,62,63,64,65,
            66,0,68,69,70,71,72,73,0,75,
            76,77,78,79,80,81,0,100,0,0,
            0,0,1,2,3,4,5,6,7,8,
            9,10,98,99,13,14,15,16,17,18,
            19,20,21,22,23,24,25,26,27,28,
            29,30,31,32,33,34,35,36,37,38,
            39,40,41,42,43,44,45,46,47,48,
            49,50,51,52,53,54,55,56,57,58,
            59,60,61,62,63,64,0,68,67,0,
            1,2,3,4,5,6,7,8,9,10,
            0,0,0,0,0,0,0,0,0,0,
            0,11,12,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            74,0,0,0,0,65,0,66,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,98,99,
            0,0,0,0
        };
    };
    public final static byte termCheck[] = TermCheck.termCheck;
    public final int termCheck(int index) { return termCheck[index]; }

    public interface TermAction {
        public final static char termAction[] = {0,
            622,673,674,675,676,677,678,679,680,681,
            682,764,765,683,685,687,689,691,693,695,
            697,699,701,703,705,707,709,711,713,715,
            717,719,721,723,725,727,729,731,733,684,
            686,688,690,692,694,696,698,700,702,704,
            706,708,710,712,714,716,718,720,722,724,
            726,728,730,732,734,761,858,857,436,842,
            846,847,851,838,853,829,830,856,831,832,
            854,833,843,839,845,834,835,836,837,855,
            859,840,841,844,848,849,850,852,762,763,
            622,673,674,675,676,677,678,679,680,681,
            682,764,765,683,685,687,689,691,693,695,
            697,699,701,703,705,707,709,711,713,715,
            717,719,721,723,725,727,729,731,733,684,
            686,688,690,692,694,696,698,700,702,704,
            706,708,710,712,714,716,718,720,722,724,
            726,728,730,732,734,761,889,888,873,874,
            878,642,882,869,884,860,861,887,862,863,
            885,864,875,870,877,865,866,867,868,886,
            890,871,872,876,879,880,881,883,762,763,
            622,673,674,675,676,677,678,679,680,681,
            682,764,765,683,685,687,689,691,693,695,
            697,699,701,703,705,707,709,711,713,715,
            717,719,721,723,725,727,729,731,733,684,
            686,688,690,692,694,696,698,700,702,704,
            706,708,710,712,714,716,718,720,722,724,
            726,728,730,732,734,761,858,857,622,842,
            846,847,851,838,853,829,830,856,831,832,
            854,833,843,839,845,834,835,836,837,855,
            859,840,841,844,848,849,850,852,762,763,
            4,673,674,675,676,677,678,679,680,681,
            682,895,896,683,685,687,689,691,693,695,
            697,699,701,703,705,707,709,711,713,715,
            717,719,721,723,725,727,729,731,733,684,
            686,688,690,692,694,696,698,700,702,704,
            706,708,710,712,714,716,718,720,722,724,
            726,728,730,732,734,894,796,795,779,780,
            784,785,789,775,791,766,767,794,768,769,
            792,770,781,776,783,771,772,773,774,793,
            797,777,778,782,786,787,788,790,622,673,
            674,675,676,677,678,679,680,681,682,671,
            672,683,685,687,689,691,693,695,697,699,
            701,703,705,707,709,711,713,715,717,719,
            721,723,725,727,729,731,733,684,686,688,
            690,692,694,696,698,700,702,704,706,708,
            710,712,714,716,718,720,722,724,726,728,
            730,732,734,670,827,826,810,811,815,816,
            820,806,822,798,799,825,800,801,823,664,
            812,807,814,802,803,804,805,824,828,808,
            809,813,817,818,819,821,622,673,674,675,
            676,677,678,679,680,681,682,671,672,683,
            685,687,689,691,693,695,697,699,701,703,
            705,707,709,711,713,715,717,719,721,723,
            725,727,729,731,733,684,686,688,690,692,
            694,696,698,700,702,704,706,708,710,712,
            714,716,718,720,722,724,726,728,730,732,
            734,670,827,826,810,811,815,816,820,806,
            822,798,799,825,800,801,823,622,812,807,
            814,802,803,804,805,824,828,808,809,813,
            817,818,819,821,622,673,674,675,676,677,
            678,679,680,681,682,764,765,683,685,687,
            689,691,693,695,697,699,701,703,705,707,
            709,711,713,715,717,719,721,723,725,727,
            729,731,733,684,686,688,690,692,694,696,
            698,700,702,704,706,708,710,712,714,716,
            718,720,722,724,726,728,730,732,734,761,
            349,622,629,628,348,635,630,631,622,636,
            308,637,632,633,639,358,622,621,622,12,
            622,1,673,674,675,676,677,678,679,680,
            681,682,762,763,683,685,687,689,691,693,
            695,697,699,701,703,705,707,709,711,713,
            715,717,719,721,723,725,727,729,731,733,
            684,686,688,690,692,694,696,698,700,702,
            704,706,708,710,712,714,716,718,720,722,
            724,726,728,730,732,734,16,316,657,2,
            673,674,675,676,677,678,679,680,681,682,
            3,622,622,622,622,622,622,622,622,622,
            622,764,765,622,622,622,622,622,622,622,
            622,622,622,622,622,622,622,622,622,622,
            622,622,622,622,622,622,622,622,622,622,
            622,622,622,622,622,622,622,622,622,622,
            622,622,622,622,622,622,622,622,622,622,
            640,622,622,622,622,761,622,662,622,622,
            622,622,622,622,622,622,622,622,622,622,
            622,622,622,622,622,622,622,622,622,622,
            622,622,622,622,622,622,622,622,762,763
        };
    };
    public final static char termAction[] = TermAction.termAction;
    public final int termAction(int index) { return termAction[index]; }
    public final int asb(int index) { return 0; }
    public final int asr(int index) { return 0; }
    public final int nasb(int index) { return 0; }
    public final int nasr(int index) { return 0; }
    public final int terminalIndex(int index) { return 0; }
    public final int nonterminalIndex(int index) { return 0; }
    public final int scopePrefix(int index) { return 0;}
    public final int scopeSuffix(int index) { return 0;}
    public final int scopeLhs(int index) { return 0;}
    public final int scopeLa(int index) { return 0;}
    public final int scopeStateSet(int index) { return 0;}
    public final int scopeRhs(int index) { return 0;}
    public final int scopeState(int index) { return 0;}
    public final int inSymb(int index) { return 0;}
    public final String name(int index) { return null; }
    public final int originalState(int state) { return 0; }
    public final int asi(int state) { return 0; }
    public final int nasi(int state) { return 0; }
    public final int inSymbol(int state) { return 0; }

    /**
     * assert(! goto_default);
     */
    public final int ntAction(int state, int sym) {
        return baseAction[state + sym];
    }

    /**
     * assert(! shift_default);
     */
    public final int tAction(int state, int sym) {
        int i = baseAction[state],
            k = i + sym;
        return termAction[termCheck[k] == sym ? k : i];
    }
    public final int lookAhead(int la_state, int sym) {
        int k = la_state + sym;
        return termAction[termCheck[k] == sym ? k : la_state];
    }
}
