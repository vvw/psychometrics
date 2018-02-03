/*
 * Copyright 2012 J. Patrick Meyer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.itemanalysis.psychometrics.reliability;

import com.itemanalysis.psychometrics.data.VariableAttributes;
import com.itemanalysis.psychometrics.polycor.CovarianceMatrix;

import java.util.ArrayList;
import java.util.Formatter;



/**
 * Computes Guttman's Lambda-2 reliability coefficient.
 * @author J. Patrick Meyer
 * @since January 29, 2008
 *
 */
public class GuttmanLambda extends AbstractScoreReliability{

	public GuttmanLambda(CovarianceMatrix matrix){
		this.matrix = matrix;
        nItems = matrix.getNumberOfVariables();
	}

    public GuttmanLambda(double[][] matrix){
        this(new CovarianceMatrix(matrix));
    }

    public ScoreReliabilityType getType(){
        return ScoreReliabilityType.GUTTMAN_LAMBDA;
    }
	
	public double value(){
		double observedScoreVariance = matrix.totalVariance();
		double lambda1 = 1-matrix.diagonalSum()/observedScoreVariance;
		double k = Double.valueOf(matrix.getNumberOfVariables()).doubleValue();
		double ssV=0.0;
		
		for(int i=0;i<nItems;i++){
			for(int j=0;j<nItems;j++){
				if(i!=j){
					ssV+=Math.pow(matrix.getCovarianceAt(i, j),2);
				}
			}
		}
		double gl = lambda1 + Math.sqrt((k/(k-1))*ssV)/observedScoreVariance;
		return gl;
	}

    private double computeSsvWithoutItemAt(int index){
        double ssV=0.0;

        for(int i=0;i<nItems;i++){
            for(int j=0;j<nItems;j++){
                if(i!=j && i!=index && j!=index){
                    ssV+=Math.pow(matrix.getCovarianceAt(i, j),2);
                }
            }
        }

        return ssV;
    }

    /**
     * Computes reliability with each item omitted in turn. The first element in the array is the
     * reliability estimate without the first item. The second item in the array is the reliability
     * estimate without the second item and so on.
     *
     * @return array of item deleted estimates.
     */
    public double[] itemDeletedReliability(){
        double[] rel = new double[nItems];
        double totalVariance = this.totalVariance();
        double diagonalSum = matrix.diagonalSum();
        double nItemsM1 = ((double)this.nItems) - 1;
        double totalVarianceAdjusted = 0;
        double diagonalSumAdjusted = 0;
        double reliabilityWithoutItem = 0;
        double ssVAdjusted = 0;
        double lambda1Adjusted = 0;


        for(int i=0;i<nItems;i++){
            //Compute item variance
            double itemVariance = matrix.getCovarianceAt(i,i);

            //Compute sum of covariance between this item and all others
            double itemCovariance = 0;
            for(int j=0;j<nItems;j++){
                if(i!=j) itemCovariance += matrix.getCovarianceAt(i,j);
            }
            itemCovariance *= 2;

            ssVAdjusted = computeSsvWithoutItemAt(i);
            totalVarianceAdjusted = totalVariance - itemCovariance - itemVariance;
            diagonalSumAdjusted = diagonalSum - itemVariance;
            lambda1Adjusted = 1-diagonalSumAdjusted/totalVarianceAdjusted;
            reliabilityWithoutItem = lambda1Adjusted + Math.sqrt((nItemsM1/(nItemsM1-1))*ssVAdjusted)/totalVarianceAdjusted;
            rel[i] = reliabilityWithoutItem;
        }

        return rel;
    }

    @Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		Formatter f = new Formatter(builder);
		String f2="%.2f";
		f.format("%21s", "Guttman's Lambda-2 = "); f.format(f2,this.value());
		return f.toString();
	}

    public String printItemDeletedSummary(ArrayList<VariableAttributes> var){
        StringBuilder sb = new StringBuilder();
        Formatter f = new Formatter(sb);
        double[] del = itemDeletedReliability();
        f.format("%-56s", " Guttman's Lambda-2 (SEM in Parentheses) if Item Deleted"); f.format("%n");
		f.format("%-56s", "========================================================"); f.format("%n");
        for(int i=0;i<del.length;i++){
            f.format("%-10s", var.get(i)); f.format("%5s", " ");
            f.format("%10.4f", del[i]); f.format("%5s", " ");f.format("%n");
        }
        return f.toString();
    }

}
