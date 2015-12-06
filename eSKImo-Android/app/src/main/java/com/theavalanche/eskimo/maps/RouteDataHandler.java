package com.theavalanche.eskimo.maps;

import com.theavalanche.eskimo.models.Route;

import java.util.Map;

/**
 * Created by jue on 11/24/15.
 */
public class RouteDataHandler {
    public Route getRoute(String userId, String eventId){
        return null;
    }

    public Map<String,Route> getRoutes(String[] userId,String eventId){
        return null;
    }

    public void saveRoute(Route route){

    }

    public static String[] PATHS = {
            "oj~nEraeqUhM@XHCWBc@Fe@@EADGd@Cb@BxF}R??`B?|EHxAr@xCRr@Hv@Bt@AjAgAtLEzAAvHArDK~CFjBAvCeLs@?bP@nF?rL@`I?jBcC?eGAwFBkH?eDAcA?SBkE?eG?aB?UCuFDsA?MEsA?aDDsB?_BEcEDuI@eEB_GAkJBuFlAsB^yE`AkCn@oFfAyDp@sCn@",
            "s_enEplpqU?zAgIEEiE?_FbA?cA??~EB~P@jK|BDeFG_EEmE?_MKkBAAuC",
            "ktwnE|}ypUYd@i@fAlAfApAhAy@|BwAfE{BnG_ArCEh@Y`Au@vBGPIXwBlG}B~Gm@tAw@xBuAfEsDpKi@tA{AjEmAlDg@rAGj@@~A?fA?dBc@Ku@EyG?uBBwA@kE?{@DaB@uA@AnC?fE@pF@tN@vEIxAIx@YpAEv@?~D@|L@vNBhL@zK?nEAxAAjI?xEG`FBbI?tEAjE@jE@jBLfJDjGFdBCjAEp@Mt@yAnGyC`LuAbF[tAy@bDOdBIfC_AhOWfEu@|DkB~JyBbLw@dEC`B@`CAbDKpPWvZGhMGfDKdNe@bx@EhIArAMbBc@bEsAtN_@tGs@tMWzE[fFyA`Wi@jJHzBHvCDdD@rK@jLBdTC|Q?bFAlAvA?|RF`BJ@pD?tK@jEAlE?pE@|DH~BV`DZtCr@jDhBfInAhFpA~Fz@|Dr@nCVx@`AxBvErJdClFhB|D?LJBnB~D|@lBrG|M~CxGpEfJbCxE|@pBvD~HfBnDdBvDfChF`DzHfCzGh@zAr@dBjBdFvBbGvAvDxEfMrJvVhAvCBFd@vA`AbC`CbGpA~DdAzBhDzGhEvK~L`[lDbJd@tAd@nABb@O\\[fAgBrFtAn@`@d@NZPz@VxA\\tBX`BRf@JJRpBn@fFn@hFz@fGp@jE`@~B~BvM~AjJtBvLnCvOhAdGPv@XrA^jAzAlDr@xAvCrElF`HdHfJ`IdKX`@BTBL^p@`@n@Tf@f@`BJj@N|AJx@N\\NV@Lf@h@~AhCd@r@FJq@z@aCrCoEtFoDpEW^d@r@z@fAt@~@fA|AnAzAnBhC|BtCbAxAvCkD`EcF|CuDVYpDuExFaH`BoBj@[l@bAh@z@p@dAtC|EdCzDnArBbA|A{DrDeJvJgAlAgBlB}A`BETBXf@t@hAzADJcBlB{B`CKOa@m@SYtC}CDG",
            "ukdoErbiqU|LkA?d@HxAJlAJlALl@P^P\\~AYfFaA`B]jA[fB_@bCg@dHwAvBc@zGCrL?dJC|C?vDC`CD`DGT?hA?LDrFEtA?TB`G?jE@dBARCpCBzHCpJ?tP?vIBvWCdO?nKCjOA`H?v@@hAB|Af@jD|@|Bn@lGhBbEtApAZl@B\\Er@Wh@c@l@_Ap@kAVYfD{BzCmB~@SlAJb@B\\JhG`BlAXv@DhEGtBM|@?l@Bp@LtDbAdAZvJlC|[|IhDbAbG|BpBt@jBj@fKzC@D@BBDj@RvDhAxBn@xEtArBfA^CjCrArC`AlA`@vGlBbAZf@Td@ZjHrE~E|Cr@b@j@LdAXt@XrA\\lAVvAPpBLhCCdICbLEVNja@O|@Fv@NhA`@pAv@z@t@xBzB|@p@TN`A`@t@Tr@JdAFtAEbCO|@Cf@BnAPd[rGz@Pl@Bz@Bp@C|AW`Bu@tB{@pA]dFi@`CURCHFLDNDPPJRJHZbBLnA@t@At@KhA?X}@dDsApEa@vA]rAKp@I|@MlCI`B[lB[zAO~@G~@?zBD|@N`B`@`E?RRhAJRZVJ@lA[X@bASt@IhEa@pJw@nDIhA?hADlAFbCXnB`@pCz@|@`@~@XnHbCXHfAZlAP`AJtADlA@nBCpFSbKYvAEzIOrDMjAE`B?fD@xAAfBAlCETQ|C?vMAjNAzCBNFD@`A?z@@`ANrAXdBj@\\PXNfAd@bARx@LtAJdCMb@Gf@Qx@UnAg@`A]fFnQ|C`K`@~@tAfCv@bANNZNRFfA@vL?nECxWA|CBl@@bAL|@RzCfAbFhB`AXnAVv@JpAJr@@vI?L?AVA\\@zE?pD?fAS?ArB",
            "_kfnE|aqqUAka@?aFBmDAuB}B?eN@wH@oL?iB@{@C[MUOOO}AcCaAsBUm@qD}LkEgOkBp@uAVsA?e@Ei@I{@WeBk@cBa@a@GmBGsBIkAEm@?}B?QLiECeDBcTAeC?cB@OKyA?yF?wD?qBD_FLyK\\sTr@uBByACyAM}@MkAYo@QeGuBQM{Ai@qBq@uCs@gB[oBQ{BEeD?_CJ{K|@_Ff@mAXuBx@mVdLaGfDyBl@_BT}DVkDPsCHwVi@aBB_AH}AZoBn@mFdBgBTm@DsA@m@AoAOiAUmA_@oHeDuBy@iAWcAMoAEqCCG?QH_BCuFKgHUgIMaEM_KQcGKo@Wc@E{@QyBi@_IoCuJkDgEuA_Cy@{CcAm@M_@IeBEoBRiA\\kB|@iDhBk@R}@Tu@DoA?UCgBk@{Bw@mCw@_Bo@eIgCcD}@sHwAaNaCeEs@sA@mBEgASgOmFeLcEgFgBgBs@qMmF{DcBeBs@qAc@[E}B?eACuI?eD?KjB{@fPaDYyE]_@Ao@Fe@PuDzBeC~Aq@Vi@Hm@BqD?_RHoC?w_@AmHB"
    };

}
